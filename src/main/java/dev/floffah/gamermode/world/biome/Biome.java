package dev.floffah.gamermode.world.biome;

import dev.floffah.gamermode.sound.Sound;

public class Biome {
    public String name;

    // -- element --
    public Precipitation precipitation;
    public float depth;
    public float temperature;
    public float scale;
    public float downfall;
    public BiomeCategory category;

    // -- effects--
    public int skyColor;
    public int waterFogColor;
    public int fogColor;
    public int waterColor;

    // -- mood_sound --
    public int moodSoundTickDelay;
    public double moodSoundOffset;
    public int moodSoundBlockSearchExtent;
    public Sound moodSound;


    public Biome() {

    }
}
