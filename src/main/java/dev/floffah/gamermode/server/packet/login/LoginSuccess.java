package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.Strings;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.UUID;

public class LoginSuccess extends BasePacket {
    public LoginSuccess() {
        super("LoginSuccess", 0x02, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + conn.playername);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String content = "";
        String currentline;
        while((currentline = in.readLine()) != null) {
            content += currentline;
        }
        in.close();
        con.disconnect();

        System.out.println(content);

        JSONObject obj = new JSONObject(content);
        String unformatted = obj.getString("id");
        String formatted = String.format("%s-%s-%s-%s-%s", unformatted.substring(0,7), unformatted.substring(7,11), unformatted.substring(11,15), unformatted.substring(15,20), unformatted.substring(20));
        UUID uuid = UUID.fromString(formatted);
        ByteBuffer uuidbytes = ByteBuffer.wrap(new byte[16]);
        uuidbytes.putLong(uuid.getMostSignificantBits());
        uuidbytes.putLong(uuid.getLeastSignificantBits());

        for(byte b : uuidbytes.array()) {
            out.writeByte(b);
        }
        Strings.writeUTF(conn.playername, out);

        return out;
    }
}
