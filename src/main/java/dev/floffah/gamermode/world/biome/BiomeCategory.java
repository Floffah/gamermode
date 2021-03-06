package dev.floffah.gamermode.world.biome;

public class BiomeCategory {
    public static BiomeCategory OCEAN = new BiomeCategory("ocean");
    public static BiomeCategory PLAINS = new BiomeCategory("plains");

    public String name;

    public BiomeCategory(String name) {
        this.name = name;
    }
}
