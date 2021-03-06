package dev.floffah.gamermode.world.biome.overworld;

import dev.floffah.gamermode.sound.Sound;
import dev.floffah.gamermode.world.biome.Biome;
import dev.floffah.gamermode.world.biome.BiomeCategory;
import dev.floffah.gamermode.world.biome.Precipitation;

public class Ocean extends Biome {
    public Ocean() {
        super();

        this.name = "minecraft:ocean";

        this.precipitation = Precipitation.RAIN;
        this.depth = -1f;
        this.temperature = 0.5f;
        this.scale = 0.1f;
        this.downfall = 0.5f;
        this.category = BiomeCategory.OCEAN;

        this.skyColor = 8103167;
        this.waterFogColor = 329011;
        this.fogColor = 12638463;
        this.waterColor = 4159204;

        this.moodSound = Sound.AmbientCave;
        this.moodSoundTickDelay = 6000;
        this.moodSoundOffset = 2d;
        this.moodSoundBlockSearchExtent = 8;
    }
}
