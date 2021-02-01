package dev.floffah.gamermode.server.socket;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.Translator;
import dev.floffah.gamermode.util.VarInt;
import org.awaitility.core.ConditionTimeoutException;

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
    DataInputStream in;
    Socket sock;
    DataOutputStream out;
    Thread closer;
    Thread packetreader;
    long lastpacket = System.currentTimeMillis();

    public SocketConnection(SocketManager main, Socket sock) throws IOException {
        this.main = main;
        this.sock = sock;
        main.server.logger.info("New connection");
        out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
        this.state = ConnectionState.HANDSHAKE;

        Runnable checkclosed = this::checkClosed;

        closer = new Thread(checkclosed);
        closer.start();

        Runnable reader = () -> {
            try {
                readPackets();
            } catch (InterruptedException | IOException e) {
                main.server.logger.printStackTrace(e);
            }
        };
        packetreader = new Thread(reader);
        packetreader.start();
    }

    public void checkClosed() {
        for (; ; ) {
            boolean closed = false;
            try {
                await().until(() -> lastpacket <= (System.currentTimeMillis() - 10000) || sock.isClosed());
            } catch (ConditionTimeoutException e) {
                continue;
            }
            while (lastpacket <= (System.currentTimeMillis() - 10000) || sock.isClosed()) {
                try {
                    close();
                    closed = true;
                    break;
                } catch (IOException e) {
                    main.server.logger.printStackTrace(e);
                }
            }
            if(closed) break;
        }
    }

    public void close() throws IOException {
        main.server.logger.info("Closing connection");
        if(packetreader != null) packetreader.interrupt();
        packetreader = null;
        if(in != null) in.close();
        if(out != null) out.close();
        in = null;
        out = null;
        if (!sock.isClosed()) {
            sock.close();
        }
        if (!verified) {
            main.newconns.remove(this);
        }
        if(closer != null) closer.interrupt();
        closer = null;
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
            main.server.logger.info(String.format("Sending packet of name %s and id %s", p.name, p.id), Arrays.toString(dat.toByteArray()));
            VarInt.writeVarInt(out, dat.toByteArray().length + 1);
            VarInt.writeVarInt(out, p.id);
            for (byte d : dat.toByteArray()) {
                out.writeByte(d);
            }
            out.flush();
        }
    }

    public void readPackets() throws IOException, InterruptedException {
        for (; ; ) {
            if(in == null || out == null) {
                break;
            }
            try {
                await().until(() -> in.available() > 0);
            } catch (ConditionTimeoutException e) {
                continue;
            }
            while (in != null && in.available() > 0) {
                int len = VarInt.readVarInt(in);
                int id = VarInt.readVarInt(in);
                byte[] data = new byte[len];

                for (int i = 0; i < len - 1; i++) {
                    try {
                        data[i] = in.readByte();
                    } catch (EOFException e) {
                        continue;
                    }
                }
                ByteArrayDataInput in = ByteStreams.newDataInput(data);
                main.server.logger.info(String.valueOf(state), Integer.toString(len), Integer.toString(id), Arrays.toString(data));
                lastpacket = System.currentTimeMillis();
                Translator.translate(len, id, in, this);
            }
        }
    }
}
