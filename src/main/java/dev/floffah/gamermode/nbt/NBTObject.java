package dev.floffah.gamermode.nbt;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.nbt.tags.NBTCompound;
import dev.floffah.gamermode.nbt.tags.NBTType;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class NBTObject {
    NBTCompound root;

    public NBTObject() {

    }

    public NBTCompound getRoot() {
        return root;
    }

    public void setRoot(NBTCompound root) {
        this.root = root;
    }

    public void read(DataInputStream in) throws IOException {
        read(in, false);
    }

    public void read(DataInputStream in, boolean gzipped) throws IOException {
        ByteArrayDataInput bin;
        if (gzipped) {
            GZIPInputStream gzin = new GZIPInputStream(in);
            bin = ByteStreams.newDataInput(gzin.readAllBytes());
        } else {
            bin = ByteStreams.newDataInput(in.readAllBytes());
        }
        this.read(bin);
    }

    public void read(ByteArrayDataInput in, boolean gzipped) throws IOException {
        read(new DataInputStream(new InputStream() {
            @Override
            public int read() throws IOException {
                return in.readByte();
            }
        }));
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
