package dev.floffah.gamermode.server.socket;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.Translator;
import dev.floffah.gamermode.util.VarInt;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import static org.awaitility.Awaitility.await;

public class SocketConnection {
    final DataInputStream in;
    public SocketManager main;
    public ConnectionState state;
    public boolean verified = false;
    public boolean serverlist = false;
    public int protver;
    public String addrused;
    public int portused;
    Socket sock;
    DataOutputStream out;
    Thread closer;
    Thread packetreader;

    public SocketConnection(SocketManager main, Socket sock) throws IOException {
        this.main = main;
        this.sock = sock;
        main.server.logger.info("New connection");
        out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
        in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
        this.state = ConnectionState.HANDSHAKE;

        Runnable checkclosed = () -> {
            await().until(() -> sock.isClosed());
            main.server.logger.info("Closing connection");
            if (!verified) {
                main.newconns.remove(this);
                main.server.logger.info("Closed connection");
            }
        };

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
        main.server.logger.info(String.format("Sending packet of name %s and id %s", p.name, p.id));
        if (dat != null) {
            VarInt.writeVarInt(out, dat.toByteArray().length + 1);
            VarInt.writeVarInt(out, p.id);
            for (byte d : dat.toByteArray()) {
                out.writeByte(d);
            }
            out.flush();
        }
    }

    public void readPackets() throws IOException, InterruptedException {
        await().until(() -> in.available() > 0);
        while (in.available() > 0) {
            int len = VarInt.readVarInt(in);
            int id = VarInt.readVarInt(in);
            byte[] data = new byte[len];

            for (int i = 0; i < len - 1; i++) {
                data[i] = in.readByte();
            }
            ByteArrayDataInput in = ByteStreams.newDataInput(data);
            main.server.logger.info(String.valueOf(state), Integer.toString(len), Integer.toString(id), Arrays.toString(data));
            Translator.translate(len, id, in, this);
        }
        readPackets();
    }
}
