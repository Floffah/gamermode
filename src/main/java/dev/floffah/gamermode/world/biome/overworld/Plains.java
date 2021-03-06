package dev.floffah.gamermode.world.biome.overworld;

import dev.floffah.gamermode.sound.Sound;
import dev.floffah.gamermode.world.biome.Biome;
import dev.floffah.gamermode.world.biome.BiomeCategory;
import dev.floffah.gamermode.world.biome.Precipitation;

public class Plains extends Biome {
    public Plains() {
        super();

        this.name = "minecraft:plains";

        this.precipitation = Precipitation.RAIN;
        this.depth = 0.125f;
        this.temperature = 0.8f;
        this.scale = 0.05f;
        this.downfall = 0.4f;
        this.category = BiomeCategory.PLAINS;

        this.skyColor = 7907327;
        this.waterFogColor = 329011;
        this.fogColor = 12638463;
        this.waterColor = 4159204;

        this.moodSound = Sound.AmbientCave;
        this.moodSoundTickDelay = 6000;
        this.moodSoundOffset = 2d;
        this.moodSoundBlockSearchExtent = 8;
    }
}
