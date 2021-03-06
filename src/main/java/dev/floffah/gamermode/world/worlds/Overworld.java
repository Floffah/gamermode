package dev.floffah.gamermode.world.worlds;

import dev.floffah.gamermode.world.World;
import dev.floffah.gamermode.world.WorldManager;
import dev.floffah.gamermode.world.biome.overworld.Ocean;
import dev.floffah.gamermode.world.biome.overworld.Plains;
import dev.floffah.gamermode.world.enums.BaseWorldType;
import dev.floffah.gamermode.world.enums.InfiniburnType;

import java.io.File;

public class Overworld extends World {
    public Overworld(WorldManager wm, File dir, String physicalName) {
        super(wm, dir);

        this.physicalName = physicalName;
        this.name = BaseWorldType.OVERWORLD.name;
        this.piglinSafe = false;
        this.natural = true;
        this.ambientLight = 0.0f;
        this.infiniburn = InfiniburnType.OVERWORLD;
        this.respawnAnchorWorks = false;
        this.hasSkylight = true;
        this.bedWorks = false;
        this.effects = BaseWorldType.OVERWORLD;
        this.hasRaids = true;
        this.logicalHeight = 256;
        this.coordinate_scale = 1.0;
        this.ultrawarm = false;
        this.hasCeiling = true;

        this.registerBiome(new Ocean());
        this.registerBiome(new Plains());
    }

    @Override
    public void uniqueGenerate() {

    }
}
