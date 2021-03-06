package dev.floffah.gamermode.server.packet.serverlist;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.chat.ChatColors;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;

import java.io.IOException;

public class Response extends BasePacket {

    public Response() {
        super("ServerListResponse", 0x00, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        String motd = conn.main.server.conf.info.motd;
        if (conn.protver >= 0 && conn.protver < conn.main.server.protver) {
            motd = String.format("&cClient version out of date (%s@C %s@S)", conn.protver, conn.main.server.protver);
        }

        Strings.writeUTF(String.format("""
                {
                    "version": {
                        "name": "GamerMode 1.16.5",
                        "protocol": %s
                    },
                    "players": {
                        "max": %s,
                        "online": 5,
                        "sample": [
                            {
                                "name": "thinkofdeath",
                                "id": "4566e69f-c907-48ee-8d71-d7ba5aa00d20"
                            }
                        ]
                    },
                    "description": {
                        "text": "%s"
                    }
                }""", conn.main.server.protver, conn.main.server.conf.players.max, ChatColors.legacyBasic('&', motd)), out);

        return out;
    }
}
