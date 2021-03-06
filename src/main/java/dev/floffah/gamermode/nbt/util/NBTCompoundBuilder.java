package dev.floffah.gamermode.nbt.util;

import dev.floffah.gamermode.nbt.tags.*;
import dev.floffah.gamermode.util.Bytes;

/**
 * The type Nbt compound builder.
 */
public class NBTCompoundBuilder {
    public String name;
    NBTBuilder builder;
    NBTCompoundBuilder parent;
    NBTListBuilder listparent;
    NBTCompound cmp;
    boolean parentmode;
    boolean listmode = false;


    /**
     * Instantiates a new NBT compound builder in builder mode.
     *
     * @param builder the builder
     */
    public NBTCompoundBuilder(NBTBuilder builder, String name) {
        this.name = name;
        this.builder = builder;
        parentmode = false;
        cmp = new NBTCompound();
        cmp.name = name;
    }

    /**
     * Instantiates a new NBT compound builder in parent mode.
     *
     * @param parent the parent
     */
    public NBTCompoundBuilder(NBTCompoundBuilder parent, String name) {
        this.name = name;
        this.parent = parent;
        parentmode = true;
        cmp = new NBTCompound();
        cmp.name = name;
    }

    public NBTCompoundBuilder(NBTListBuilder parent) {
        parentmode = false;
        listmode = true;
        listparent = parent;
        this.name = "";
        cmp = new NBTCompound();
        cmp.name = "";
    }

    public NBTCompoundBuilder setByte(String name, byte value) {
        NBTByte nbyte = new NBTByte();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setBool(String name, boolean value) {
        NBTByte nbyte = new NBTByte();
        nbyte.name = name;
        nbyte.value = Bytes.bool(value);
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setByteArray(String name, byte[] value) {
        NBTByteArray nbyte = new NBTByteArray();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder startCompound(String name) {
        return new NBTCompoundBuilder(this, name);
    }

    public NBTCompoundBuilder setDouble(String name, double value) {
        NBTDouble nbyte = new NBTDouble();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setFloat(String name, float value) {
        NBTFloat nbyte = new NBTFloat();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setInt(String name, int value) {
        NBTInt nbyte = new NBTInt();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setIntArray(String name, int[] value) {
        NBTIntArray nbyte = new NBTIntArray();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTListBuilder startList(String name, NBTType type) {
        return new NBTListBuilder(this, name, type);
    }

    public NBTCompoundBuilder setLong(String name, long value) {
        NBTLong nbyte = new NBTLong();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setLongArray(String name, long[] value) {
        NBTLongArray nbyte = new NBTLongArray();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setShort(String name, short value) {
        NBTShort nbyte = new NBTShort();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    public NBTCompoundBuilder setString(String name, String value) {
        NBTString nbyte = new NBTString();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Build the NBT compound and get the parent NBT compound builder.
     * Do not use this if this NBT compound builder is the root compound in an NBT builder.
     * In that case, use end() instead
     *
     * @return the parent nbt compound builder
     */
    public NBTCompoundBuilder build() {
        if (parentmode && !listmode) {
            parent.cmp.data.put(cmp.name, cmp);
            return parent;
        }
        return null;
    }

    /**
     * Build the NBT compound and get the parent NBT list builder. Do not use if this NBT compound builder is the root compound in and NBT builder or nested inside another NBT compound builder.
     *
     * @return the parent nbt list builder
     */
    public NBTListBuilder listBuild() {
        if (!parentmode && listmode) {
            listparent.lst.value.add(cmp);
            return listparent;
        }
        return null;
    }

    /**
     * End the NBT compound builder and get the parent NBT builder.
     *
     * @return the parent nbt builder
     */
    public NBTBuilder end() {
        if (!parentmode && !listmode) {
            return builder;
        }
        return null;
    }
}
