package dev.floffah.gamermode.world;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.nbt.NBTObject;
import dev.floffah.gamermode.nbt.tags.NBTType;
import dev.floffah.gamermode.nbt.util.NBTBuilder;
import dev.floffah.gamermode.nbt.util.NBTCompoundBuilder;
import dev.floffah.gamermode.nbt.util.NBTListBuilder;
import dev.floffah.gamermode.server.Server;
import dev.floffah.gamermode.world.biome.Biome;
import dev.floffah.gamermode.world.worlds.Overworld;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldManager {
    public NBTObject codec;
    Server server;
    HashMap<String, World> worlds = new HashMap<>();
    ArrayList<Biome> allBiomes = new ArrayList<>();
    String defWorld = null;

    public WorldManager(Server server) {
        this.server = server;
    }

    public void startUp() {
        server.logger.info("Loading worlds");
        File worlddir = Path.of(server.parent, server.conf.worlds.worldname).toFile();
        World world = new Overworld(this, worlddir, server.conf.worlds.worldname);
        world.physicalName = server.conf.worlds.worldname;
        defWorld = world.physicalName;
        register(world);

        try {
            writeDimCodec();
        } catch (IOException e) {
            server.logger.printStackTrace(e);
        }
    }

    public World getDefaultWorld() {
        return worlds.get(defWorld);
    }

    public void register(World world) {
        world.loadFromDirectory();
        worlds.put(world.physicalName, world);
    }

    public void loadDefaultWorlds() {

    }

    public NBTObject getPlainDimType(World wrld) {
        NBTCompoundBuilder main = NBTBuilder.start("");
        writeDimType(main, wrld);
        return main.end().build();
    }

    public void writeDimCodec() throws IOException {
        codec = buildDimCodec();
        File dimfile = Path.of(server.datadir.toString(), "codec.dat").toFile();
        FileOutputStream out = new FileOutputStream(dimfile);
        ByteArrayDataOutput dimbytes = ByteStreams.newDataOutput();
        codec.write(dimbytes);
        out.write(dimbytes.toByteArray());
    }

    @Nullable
    public World getWorld(String name) {
        return worlds.get(name);
    }

    public void writeDimType(NBTCompoundBuilder world, World wrld) {
        world.setBool("piglin_safe", wrld.piglinSafe)
                .setBool("natural", wrld.natural)
                .setFloat("ambient_light", wrld.ambientLight)
                .setString("infiniburn", wrld.infiniburn.name)
                .setBool("respawn_anchor_works", wrld.respawnAnchorWorks)
                .setBool("has_skylight", wrld.hasSkylight)
                .setBool("bed_works", wrld.bedWorks)
                .setString("effects", wrld.effects.name)
                .setBool("has_raids", wrld.hasRaids)
                .setInt("logical_height", wrld.logicalHeight)
                .setDouble("coordinate_scale", wrld.coordinate_scale)
                .setBool("ultrawarm", wrld.ultrawarm)
                .setBool("has_ceiling", wrld.hasCeiling);
    }

    public NBTObject buildDimCodec() {
        //root
        NBTCompoundBuilder main = NBTBuilder.start("");
        //dimtype
        NBTCompoundBuilder cb = main.startCompound("minecraft:dimension_type");
        cb.setString("type", "minecraft:dimension_type");
        //value
        NBTListBuilder lst = cb.startList("value", NBTType.COMPOUND);

        int index = 0;
        for (Map.Entry<String, World> w : worlds.entrySet()) {
            NBTCompoundBuilder world = new NBTCompoundBuilder(lst);
            World wrld = w.getValue();
            //root array part
            NBTCompoundBuilder el = world.setString("name", wrld.name)
                    .setInt("id", index)
                    // element
                    .startCompound("element");

            writeDimType(el, wrld);

            el.build()
                    .listBuild();


            index++;
        }

        //worldgen
        NBTCompoundBuilder gn = main.startCompound("minecraft:worldgen/biome");
        gn.setString("type", "minecraft:worldgen/biome");

        NBTListBuilder gen = gn.startList("value", NBTType.COMPOUND);

        int bindex = 0;
        for (Biome b : allBiomes) {
            NBTCompoundBuilder biom = new NBTCompoundBuilder(lst);

            biom.
                    setString("name", b.name)
                    .setInt("id", index)
                    .startCompound("element")
                    .setString("precipitation", b.precipitation.name)
                    .startCompound("effects")
                    .setInt("sky_color", b.skyColor)
                    .setInt("water_fog_color", b.waterFogColor)
                    .setInt("fog_color", b.fogColor)
                    .setInt("water_color", b.waterColor)
                    .startCompound("mood_sound")
                    .setInt("mood_sound", b.moodSoundTickDelay)
                    .setDouble("offset", b.moodSoundOffset)
                    .setString("sound", b.moodSound.name)
                    .setInt("block_search_extend", b.moodSoundBlockSearchExtent)
                    .build() // mood_sound
                    .build() // effects
                    .setFloat("depth", b.depth)
                    .setFloat("temperature", b.temperature)
                    .setFloat("scale", b.scale)
                    .setFloat("downfall", b.downfall)
                    .setString("category", b.category.name)
                    .build() // element
                    .listBuild();

            index++;
        }


        return lst.build().build().end().build();
    }
}
