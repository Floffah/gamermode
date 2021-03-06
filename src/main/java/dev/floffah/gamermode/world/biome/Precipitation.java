package dev.floffah.gamermode.world.biome;

public class Precipitation {
    public static Precipitation NONE = new Precipitation("none");
    public static Precipitation RAIN = new Precipitation("rain");

    public String name;

    public Precipitation(String name) {
        this.name = name;
    }
}
