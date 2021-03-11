package dev.floffah.gamermode.server.packet.play.message;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.events.message.PluginMessageReceivedEvent;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PluginMessage extends BasePacket {
    public ByteArrayDataOutput bytes;
    public byte[] bytesread;
    public String channel;

    public PluginMessage(String channel, ByteArrayDataOutput bytes) {
        super("PluginMessageOut", 0x17, PacketType.OUTBOUND);
        this.channel = channel;
        this.bytes = bytes;
    }

    public PluginMessage() {
        super("PluginMessageIn", 0x0B, PacketType.INBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        Strings.writeUTF(channel, out);
        out.write(bytes.toByteArray());

        return out;
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        this.channel = Strings.readUTF(in);
        System.out.println(this.channel);
        int restlen = 3 + (this.channel.getBytes(StandardCharsets.UTF_8).length);
        byte[] bread = new byte[restlen];
        for (int i = 0; i < restlen; i++) {
            System.out.println(i + " " + restlen);
            bread[i] = in.readByte();
        }
        this.bytesread = bread;
        if (channel.equals("minecraft:brand")) {
            ByteArrayDataInput dat = ByteStreams.newDataInput(bread);
            String brand = dat.readUTF();
            conn.player.brand = brand;
            System.out.println(brand);
        }
        conn.main.server.events.execute(new PluginMessageReceivedEvent(this));
    }
}
