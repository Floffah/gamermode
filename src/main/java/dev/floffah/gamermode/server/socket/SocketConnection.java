package dev.floffah.gamermode.server.socket;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.Translator;
import dev.floffah.gamermode.util.Bytes;
import dev.floffah.gamermode.util.VarInt;
import org.awaitility.core.ConditionTimeoutException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.net.Socket;
import java.security.KeyPair;
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
    public String playername;
    public byte[] verifytoken = null;
    public boolean encrypted = false;
    public KeyPair kp;
    public byte[] ssecret;
    public Cipher decryptc;
    public Cipher encryptc;
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

        main.pool.execute(checkclosed);

        Runnable reader = () -> {
            try {
                readPackets();
            } catch (InterruptedException | IOException e) {
                main.server.logger.printStackTrace(e);
            }
        };
        main.pool.execute(reader);
    }

    public void checkClosed() {
        for (; ; ) {
            boolean closed = false;
            if(stopcloser) {
                break;
            }
            try {
                await().until(() -> lastpacket <= (System.currentTimeMillis() - 10000) || sock.isClosed());
            } catch (ConditionTimeoutException e) {
                continue;
            }
            while (stopcloser || lastpacket <= (System.currentTimeMillis() - 10000) || sock.isClosed()) {
                if(stopcloser) {
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
            if(stopcloser) {
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
        p.conn = this;
        ByteArrayDataOutput dat = p.buildOutput();
        if (dat != null) {
            String dbgp = "";
            if(this.encrypted) {
                dbgp += "(Encrypted) ";
            }
            main.server.logger.info(String.format("%sSending packet of name %s and id %s", dbgp, p.name, p.id), Arrays.toString(dat.toByteArray()));
            if (encrypted) {
                VarInt.writeEncryptedVarInt(out, dat.toByteArray().length + 1, encryptc);
                VarInt.writeEncryptedVarInt(out, p.id, encryptc);
            } else {
                VarInt.writeVarInt(out, dat.toByteArray().length + 1);
                VarInt.writeVarInt(out, p.id);
            }
            for (byte d : dat.toByteArray()) {
                if(encrypted) {
                    try {
                        out.writeByte(encryptc.doFinal(Bytes.byteToArray(d))[0]);
                    } catch (IllegalBlockSizeException | BadPaddingException e) {
                        main.server.logger.printStackTrace(e);
                    }
                } else {
                    out.writeByte(d);
                }
            }
            out.flush();
        }
    }

    public void readPackets() throws IOException, InterruptedException {
        for (; ; ) {
            if(stopreader) {
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
                if(stopreader) {
                    break;
                }
                int len;
                int id;
                if (encrypted) {
                    len = VarInt.readEncryptedVarInt(in, decryptc);
                    id = VarInt.readEncryptedVarInt(in, decryptc);
                } else {
                    len = VarInt.readVarInt(in);
                    id = VarInt.readVarInt(in);
                }
                byte[] data = new byte[len];

                for (int i = 0; i < len - 1; i++) {
                    try {
                        if (encrypted) {
                            data[i] = decryptc.doFinal(Bytes.byteToArray(in.readByte()))[0];
                        } else {
                            data[i] = in.readByte();
                        }
                    } catch (EOFException | BadPaddingException | IllegalBlockSizeException e) {
                        continue;
                    }
                }
                ByteArrayDataInput in = ByteStreams.newDataInput(data);
                main.server.logger.info(String.valueOf(this.encrypted), String.valueOf(state), Integer.toString(len), Integer.toString(id), Arrays.toString(data));
                lastpacket = System.currentTimeMillis();
                Translator.translate(len, id, in, this);
            }
            if(stopreader) {
                break;
            }
        }
    }
}
