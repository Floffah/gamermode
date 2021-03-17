package dev.floffah.gamermode.nbt.util;

import dev.floffah.gamermode.nbt.tags.*;
import dev.floffah.gamermode.util.Bytes;
import lombok.Getter;
import lombok.Setter;

/**
 * A NBT builder class for building nested compounds
 */
public class NBTCompoundBuilder {
    /**
     * The compound's name
     * -- GETTER --
     * Get the compound's name
     * @return The compound's name
     * -- SETTER --
     * Set the compound's name
     * @param name The new name
     */
    @Getter
    @Setter
    String name;
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

    /**
     * Instantiates a new NBT compound builder in parent mode and as a root compound
     * @param parent The parent nbt builder
     */
    public NBTCompoundBuilder(NBTListBuilder parent) {
        parentmode = false;
        listmode = true;
        listparent = parent;
        this.name = "";
        cmp = new NBTCompound();
        cmp.name = "";
    }

    /**
     * Put a byte into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setByte(String name, byte value) {
        NBTByte nbyte = new NBTByte();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put a byte (0|1 representing a boolean) into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setBool(String name, boolean value) {
        NBTByte nbyte = new NBTByte();
        nbyte.name = name;
        nbyte.value = Bytes.bool(value);
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put a byte array into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setByteArray(String name, byte[] value) {
        NBTByteArray nbyte = new NBTByteArray();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Create a nested NBT compound and get it. Run {@link NBTCompoundBuilder#build()} to get back to this NBTCompoundBuilder.
     * @param name The compound being created's name
     * @return The created NBTCompoundBuilder
     */
    public NBTCompoundBuilder startCompound(String name) {
        return new NBTCompoundBuilder(this, name);
    }

    /**
     * Put a double into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setDouble(String name, double value) {
        NBTDouble nbyte = new NBTDouble();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put a float into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setFloat(String name, float value) {
        NBTFloat nbyte = new NBTFloat();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put an int into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setInt(String name, int value) {
        NBTInt nbyte = new NBTInt();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put an int array into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setIntArray(String name, int[] value) {
        NBTIntArray nbyte = new NBTIntArray();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Create a nested NBT list and get it. Run {@link NBTListBuilder#build()} to get back to this NBTCompoundBuilder.
     * @param name The list being created's name
     * @return The created NBTListBuilder
     */
    public NBTListBuilder startList(String name, NBTType type) {
        return new NBTListBuilder(this, name, type);
    }

    /**
     * Put a double into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setLong(String name, long value) {
        NBTLong nbyte = new NBTLong();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put a long array into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setLongArray(String name, long[] value) {
        NBTLongArray nbyte = new NBTLongArray();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put a short into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setShort(String name, short value) {
        NBTShort nbyte = new NBTShort();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Put a string into the compound with a given name
     * @param name Tag name
     * @param value Tag value
     * @return The current NBTCompoundBuilder
     */
    public NBTCompoundBuilder setString(String name, String value) {
        NBTString nbyte = new NBTString();
        nbyte.name = name;
        nbyte.value = value;
        cmp.data.put(name, nbyte);
        return this;
    }

    /**
     * Build the NBT compound and get the parent NBT compound builder.<br/>
     * Do not use this if the current NBT compound builder is the root compound in an NBT builder.
     * In that case, use {@link NBTCompoundBuilder#end()} instead
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
     * Build the NBT compound and get the parent NBT list builder.<br/>
     * Do not use if this NBT compound builder is the root compound in and NBT builder or nested inside another NBT compound builder.<br/>
     * In that case, use {@link NBTCompoundBuilder#end()} or {@link NBTCompoundBuilder#build()}
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
