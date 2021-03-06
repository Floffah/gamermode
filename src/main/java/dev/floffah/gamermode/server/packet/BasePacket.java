package dev.floffah.gamermode.server.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import dev.floffah.gamermode.server.socket.SocketConnection;

import java.io.IOException;

public class BasePacket {
    public String name;
    public int id;
    public SocketConnection conn;
    PacketType type;

    public BasePacket(String name, int id, PacketType type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public void process(int len, ByteArrayDataInput in) throws IOException {
    }

    public ByteArrayDataOutput buildOutput() throws IOException {
        return null;
    }
}
