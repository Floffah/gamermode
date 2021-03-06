package dev.floffah.gamermode.world;

import com.google.errorprone.annotations.ForOverride;
import dev.floffah.gamermode.world.biome.Biome;
import dev.floffah.gamermode.world.enums.BaseWorldType;
import dev.floffah.gamermode.world.enums.InfiniburnType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class World {
    public String name;
    public String physicalName;
    // -- type values --
    public boolean piglinSafe;
    public boolean natural;
    public float ambientLight;
    public InfiniburnType infiniburn;
    public boolean respawnAnchorWorks;
    public boolean hasSkylight;
    public boolean bedWorks;
    public BaseWorldType effects;
    public boolean hasRaids;
    public int logicalHeight;
    public double coordinate_scale;
    public boolean ultrawarm;
    public boolean hasCeiling;
    // -- reference --
    public ArrayList<Biome> biomeTypes = new ArrayList<>();
    WorldManager wm;
    File dir;

    public World(WorldManager wm, File dir) {
        this.wm = wm;
        this.dir = dir;
    }

    public void loadFromDirectory() {
        if (!dir.exists()) {
            generate();
            return;
        }
    }

    public void backup() throws IOException {
        wm.server.logger.warn(String.format("Creating backup of word '%s'", name));
        File backupdir = Path.of(wm.server.parent, "backups", name).toFile();
        Files.move(dir.toPath(), backupdir.toPath());
    }

    public void registerBiome(Biome biome) {
        this.biomeTypes.add(biome);
        this.wm.allBiomes.add(biome);
    }

    public void generate() {
        if (!dir.exists()) dir.mkdirs();
        uniqueGenerate();
    }

    @ForOverride
    public void uniqueGenerate() {

    }
}
