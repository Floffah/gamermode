package dev.floffah.gamermode.player;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Profile {
    /**
     * Name to uuid
     */
    public static String NameConvertURL = "https://api.mojang.com/profiles/minecraft";
    /**
     * Profiles
     */
    public static String ProfileURL = "https://sessionserver.mojang.com/session/minecraft/profile";

    /**
     * The player
     * -- GETTER --
     * Get the player
     * @return The player
     */
    @Getter
    Player player;

    /**
     * Construct a profile
     * @param player The associated player
     */
    public Profile(Player player) {
        this.player = player;
    }

    /**
     * Authentication process
     * @throws IOException Any exception thrown during authentication
     */
    public void authenticate() throws IOException {
        // todo: implement some kind of temporary caching for these values
        JSONArray id = requestUUID(player.username);
        if (id.length() >= 1) {
            JSONObject pinf = id.getJSONObject(0);
            String unfid = pinf.getString("id");
            String formatted = String.format("%s-%s-%s-%s-%s", unfid.substring(0, 7), unfid.substring(7, 11), unfid.substring(11, 15), unfid.substring(15, 20), unfid.substring(20));
            player.uniqueId = UUID.fromString(formatted);
            //JSONObject inf = getInfo(player.uuid);
//            if (!inf.getString("name").equals(player.username) && !inf.getString("name").equals(pinf.getString("name"))) {
//                player.conn.close();
//                throw new IOException("Did not work rip");
//            }
        }
    }

    /**
     * Get the user's info
     * @param uuid UUID
     * @return Info from the UUID
     * @throws IOException Any exception thrown when getting the player info
     */
    public JSONObject getInfo(UUID uuid) throws IOException {
        System.out.println(ProfileURL + "/" + uuid.toString());
        URL url = new URL(ProfileURL + "/" + uuid.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        String content = readHttpConString(con);
        System.out.println(content);

        return new JSONObject(content);
    }

    /**
     * Gets the uuid from the supplied username
     * @param username Username to request
     * @return UUIDs
     * @throws IOException Any exceptions thrown when requesting the uuid
     */
    public JSONArray requestUUID(String username) throws IOException {
        URL url = new URL(NameConvertURL);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        JSONArray names = new JSONArray();
        names.put(username);

        OutputStream out = con.getOutputStream();
        out.write(names.toString().getBytes(StandardCharsets.UTF_8));

        return new JSONArray(readHttpConString(con));
    }

    /**
     * Builds a string from a http url connection
     * @param con The connection
     * @return String built
     * @throws IOException And exception thrown along the way
     */
    public String readHttpConString(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String current;
        while ((current = in.readLine()) != null) {
            content.append(current);
        }
        in.close();
        con.disconnect();

        return content.toString();
    }
}
