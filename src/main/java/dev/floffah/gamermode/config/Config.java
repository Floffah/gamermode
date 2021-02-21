package dev.floffah.gamermode.config;

public class Config {
    public int configVersion = 1001;

    public PlayerConf players = new PlayerConf();
    public ServerInfo info = new ServerInfo();

//    public void setPlayers(PlayerConf players) {
//        this.players = players;
//    }
//
//    public PlayerConf getPlayers() {
//        return players;
//    }

    public static class PlayerConf {
        public int max = 20;

//        public void setMax(int max) {
//            this.max = max;
//        }
//
//        public int getMax() {
//            return max;
//        }
    }

    public static class ServerInfo {
        public String motd = "Minecraft server";
    }
}
