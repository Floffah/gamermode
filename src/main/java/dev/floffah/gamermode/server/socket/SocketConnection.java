package dev.floffah.gamermode.server.socket;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.chat.ChatColors;
import dev.floffah.gamermode.chat.Component;
import dev.floffah.gamermode.events.network.PacketSendingEvent;
import dev.floffah.gamermode.events.network.PacketSentEvent;
import dev.floffah.gamermode.player.Player;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.Translator;
import dev.floffah.gamermode.server.packet.connection.Disconnect;
import dev.floffah.gamermode.server.packet.connection.LoginDisconnect;
import dev.floffah.gamermode.util.Bytes;
import dev.floffah.gamermode.util.VarInt;
import org.awaitility.core.ConditionTimeoutException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.util.Arrays;

import static org.awaitility.Awaitility.await;

public class SocketConnection {
    public SocketManager main;
    public ConnectionState state;
    public boolean verified = false;
    public boolean serverlist = false;
    public int protver;
    public String addrused;
    public int portused;
    public byte[] verifytoken = null;
    public boolean encrypted = false;
    public KeyPair kp;
    public SecretKey secret;
    public Cipher eciph;
    public Cipher dciph;
    public MessageDigest digest;
    public Player player;
    public String session;
    public String hash;
    public String prox;
    DataInputStream in;
    Socket sock;
    DataOutputStream out;
    long lastpacket = System.currentTimeMillis();

    boolean stopcloser = false;
    boolean stopreader = false;

    public SocketConnection(SocketManager main, Socket sock) throws IOException {
        this.main = main;
        this.sock = sock;
        main.server.logger.info("New connection");
        out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
        this.state = ConnectionState.HANDSHAKE;

        Runnable checkclosed = this::checkClosed;

        main.server.pool.execute(checkclosed);

        Runnable reader = () -> {
            try {
                readPackets();
            } catch (InterruptedException | IOException e) {
                main.server.logger.printStackTrace(e);
            }
        };
        main.server.pool.execute(reader);
    }

    public void checkClosed() {
        for (; ; ) {
            boolean closed = false;
            if (stopcloser) {
                break;
            }
            try {
                await().until(() -> lastpacket <= (System.currentTimeMillis() - 10000) || sock.isClosed());
            } catch (ConditionTimeoutException e) {
                continue;
            }
            while (stopcloser || lastpacket <= (System.currentTimeMillis() - 10000) || sock.isClosed()) {
                if (stopcloser) {
                    break;
                }
                try {
                    close();
                    closed = true;
                    break;
                } catch (IOException e) {
                    main.server.logger.printStackTrace(e);
                }
            }
            if (stopcloser) {
                break;
            }
            if (closed) break;
        }
    }

    public void close() throws IOException {
        main.server.logger.info("Closing connection");
        stopcloser = true;
        if (in != null) in.close();
        if (out != null) out.close();
        in = null;
        out = null;
        if (!sock.isClosed()) {
            sock.close();
        }
        if (!verified) {
            main.newconns.remove(this);
        }
        stopreader = true;
    }

    public void send(ByteArrayDataOutput out) throws IOException {
        byte[] data = out.toByteArray();
        for (byte d : data) {
            this.out.writeByte(d);
        }
        this.out.flush();
    }

    public void send(BasePacket p) throws IOException {
        send(p, false);
    }

    public void disconnect(String message) {
        Component reason = ChatColors.translateLegacy(message, '&');
        disconnect(reason);
    }

    public void disconnect(Component reason) {
        try {
            if (state == ConnectionState.PLAY) {
                send(new Disconnect(reason));
            } else if (state == ConnectionState.LOGIN) {
                send(new LoginDisconnect(reason));
            }
            close();
        } catch (IOException e) {
            main.server.logger.printStackTrace(e);
        }
    }

    public void send(BasePacket p, boolean disableEncryption) throws IOException {
        p.conn = this;
        ByteArrayDataOutput dat = p.buildOutput();
        PacketSendingEvent event = new PacketSendingEvent(p, dat);
        main.server.events.execute(event);
        if (event.isCancelled()) return;
        ByteArrayDataOutput prc = ByteStreams.newDataOutput();
        if (dat != null) {
            String dbgp = "";
            if (this.encrypted && !disableEncryption) {
                dbgp += "(Encrypted) ";
            }
            main.server.logger.info(String.format("%sSending packet of name %s and id %s (current length %s)", dbgp, p.name, p.id, dat.toByteArray().length), Arrays.toString(dat.toByteArray()));
            VarInt.writeVarInt(prc, dat.toByteArray().length + 1);
            VarInt.writeVarInt(prc, p.id);
            prc.write(dat.toByteArray());
            byte[] sent;
            if (encrypted && !disableEncryption) {
                try {
                    byte[] ecsend = eciph.update(prc.toByteArray());
                    if (ecsend != null) sent = ecsend;
                    else {
                        main.server.logger.info(String.format("Could not send packet of name %s and id %s because encryption was null", p.name, p.id));
                        return;
                    }
                } catch (Exception e) {
                    main.server.logger.printStackTrace(e);
                    return;
                }
            } else {
                sent = prc.toByteArray();
            }
            out.write(sent);

            main.server.logger.info(String.format("%sSent packet of name %s and id %s of length %s", dbgp, p.name, p.id, sent.length), Arrays.toString(sent));
            out.flush();
            PacketSentEvent sente = new PacketSentEvent(p, prc);
            p.postSend(sente);
            main.server.events.execute(sente);
        }
    }

    public void readPackets() throws IOException, InterruptedException {
        for (; ; ) {
            if (stopreader) {
                break;
            }
            if (in == null || out == null) {
                break;
            }
            try {
                await().until(() -> in.available() > 0);
            } catch (ConditionTimeoutException e) {
                continue;
            }
            while (stopreader || (in != null && in.available() > 0)) {
                if (stopreader) {
                    break;
                }
                int len;
                int id;
                if (encrypted) {
                    len = VarInt.readEncryptedVarInt(in, dciph);
                    id = VarInt.readEncryptedVarInt(in, dciph);
                } else {
                    len = VarInt.readVarInt(in);
                    id = VarInt.readVarInt(in);
                }
                byte[] data = new byte[len];

                for (int i = 0; i < len - 1; i++) {
                    try {
                        if (encrypted) {
                            data[i] = dciph.update(Bytes.byteToArray(in.readByte()))[0];
                        } else {
                            data[i] = in.readByte();
                        }
                    } catch (EOFException e) {
                        continue;
                    }
                }
                ByteArrayDataInput in = ByteStreams.newDataInput(data);
                main.server.logger.info(String.valueOf(this.encrypted), String.valueOf(state), Integer.toString(len), Integer.toString(id), Arrays.toString(data));
                lastpacket = System.currentTimeMillis();
                Translator.translate(len, id, in, this);
            }
            if (stopreader) {
                break;
            }
        }
    }
}
