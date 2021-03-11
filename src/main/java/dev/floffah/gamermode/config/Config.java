package dev.floffah.gamermode.config;

public class Config {
    public int configVersion = 1001;

    public PlayerConf players = new PlayerConf();
    public ServerInfo info = new ServerInfo();
    public WorldInfo worlds = new WorldInfo();
    public Debug debug = new Debug();
    public Performance performance = new Performance();

    public static class PlayerConf {
        public int max = 20;
    }

    public static class ServerInfo {
        public String motd = "Minecraft server";
        public byte difficulty = 1;
    }

    public static class WorldInfo {
        public String worldname = "world";
        public int renderDistance = 8;
    }

    public static class Debug {
        public boolean debugLog = true;
    }

    public static class Performance {
        public int poolSize = 10;
        public int scheduledPoolSize = 10;
    }
}
