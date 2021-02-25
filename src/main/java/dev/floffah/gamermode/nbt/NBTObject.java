package dev.floffah.gamermode.nbt;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.nbt.tags.NBTCompound;
import dev.floffah.gamermode.nbt.tags.NBTTag;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.TreeMap;

public class NBTObject {
    TreeMap<String, NBTTag> data = new TreeMap<>();

    public NBTObject() {

    }

    public void read(DataInputStream in) throws IOException {
        ByteArrayDataInput bin = ByteStreams.newDataInput(in.readAllBytes());
        this.read(bin);
    }

    public void read(ByteArrayDataInput in) {
        for(; ;) {
            byte type;

            try {
                type = in.readByte();
            } catch(Exception e) {
                break;
            }

            if(type == 0x0a) {
                NBTCompound compound = NBTCompound.fromByteArray(in);
                data.put(compound.name, compound);
            } else if(type == 0x00) {
                break;
            }
        }
    }
}
