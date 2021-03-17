package dev.floffah.gamermode.nbt;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.nbt.tags.NBTCompound;
import dev.floffah.gamermode.nbt.tags.NBTType;
import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * The main clas for creating or parsing NBT
 */
public class NBTObject {
    /**
     * The root NBT Compound structure
     * -- GETTER --
     * Get the root NBT compound
     * @return The root NBT compound
     * -- SETTER --
     * Set the root NBT compound
     * @param root The new root NBT compound
     */
    @Getter
    @Setter
    NBTCompound root;

    /**
     * Create a new NBTObject
     */
    public NBTObject() {

    }

    /**
     * Read an uncompressed NBT object from an input stream
     * @param in The input stream
     * @throws IOException Throws any exception from the parsing process
     */
    public void read(InputStream in) throws IOException {
        read(in, false);
    }

    /**
     * Read an uncompressed or compressed NBT object from an input stream. Does not automatically detect if the input stream is compressed
     * @param in The input stream
     * @param gzipped Whether or not the stream is zipped
     * @throws IOException Throws any exception from the parsing/unzipping process
     */
    public void read(InputStream in, boolean gzipped) throws IOException {
        ByteArrayDataInput bin;
        if (gzipped) {
            GZIPInputStream gzin = new GZIPInputStream(in);
            bin = ByteStreams.newDataInput(gzin.readAllBytes());
        } else {
            bin = ByteStreams.newDataInput(in.readAllBytes());
        }
        this.read(bin);
    }

    /**
     * Read an uncompressed or compressed NBT object from a byte array
     * @param in The byte array to read from
     * @param gzipped Whether or not the byte array is gzipped
     * @throws IOException Throws any exception from the parsing/unzipping process
     */
    public void read(ByteArrayDataInput in, boolean gzipped) throws IOException {
        read(new DataInputStream(new InputStream() {
            @Override
            public int read() throws IOException {
                return in.readByte();
            }
        }));
    }

    /**
     * Read an uncompressed NBT object from a byte array
     * @param in The byte array to read from
     */
    public void read(ByteArrayDataInput in) {
        in.skipBytes(1);
        this.root = NBTCompound.fromByteArray(in, true);
    }

    /**
     * Write a built NBTObject to a byte array
     * @param out The byte array to write to
     */
    public void write(ByteArrayDataOutput out) {
        out.writeByte(NBTType.COMPOUND.ordinal());
        root.toByteArray(out, true);
    }
}
