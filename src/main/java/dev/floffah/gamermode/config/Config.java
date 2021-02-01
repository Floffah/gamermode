package dev.floffah.gamermode.config;

public class Config {
    public PlayerConf players;

    public void setPlayers(PlayerConf players) {
        this.players = players;
    }

    public PlayerConf getPlayers() {
        return players;
    }

    public static class PlayerConf {
        public int max;

        public void setMax(int max) {
            this.max = max;
        }

        public int getMax() {
            return max;
        }
    }
}
