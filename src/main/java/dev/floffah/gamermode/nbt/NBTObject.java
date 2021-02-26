package dev.floffah.gamermode.nbt;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.nbt.tags.NBTCompound;
import dev.floffah.gamermode.nbt.tags.NBTType;

import java.io.DataInputStream;
import java.io.IOException;

public class NBTObject {
    NBTCompound root;

    public NBTObject() {

    }

    public void read(DataInputStream in) throws IOException {
        ByteArrayDataInput bin = ByteStreams.newDataInput(in.readAllBytes());
        this.read(bin);
    }

    public void read(ByteArrayDataInput in) {
        in.skipBytes(1);
        this.root = NBTCompound.fromByteArray(in, true);
    }

    public void write(ByteArrayDataOutput out) {
        out.writeByte(NBTType.COMPOUND.ordinal());
        root.toByteArray(out, true);
    }
}
