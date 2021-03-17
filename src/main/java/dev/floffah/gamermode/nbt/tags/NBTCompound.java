package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class NBTCompound extends NBTTag {
    /**
     * The tag's data
     * -- GETTER --
     * Get the tag's data
     * @return The tags data
     * -- SETTER --
     * Set the tag's data
     * @param data The new data
     */
    @Getter
    @Setter
    public HashMap<String, NBTTag> data = new HashMap<>();

    /**
     * Construct an NBTCompound
     */
    public NBTCompound() {
        super(NBTType.COMPOUND);
    }

    /**
     * Create an NBTCompound from an optionally named compound
     * @param in Byte array
     * @param named Whether or not the tag is named
     * @return The constructed compound
     */
    @NotNull
    public static NBTCompound fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTCompound compound = new NBTCompound();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }
            compound.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        for (; ; ) {
            byte type = in.readByte();

            NBTTag tag = null;

            if (type == NBTType.END.ordinal()) break;
            else if (type == NBTType.BYTE.ordinal()) tag = NBTByte.fromByteArray(in, true);
            else if (type == NBTType.SHORT.ordinal()) tag = NBTShort.fromByteArray(in, true);
            else if (type == NBTType.INT.ordinal()) tag = NBTInt.fromByteArray(in, true);
            else if (type == NBTType.LONG.ordinal()) tag = NBTLong.fromByteArray(in, true);
            else if (type == NBTType.FLOAT.ordinal()) tag = NBTFloat.fromByteArray(in, true);
            else if (type == NBTType.DOUBLE.ordinal()) tag = NBTDouble.fromByteArray(in, true);
            else if (type == NBTType.BYTE_ARRAY.ordinal()) tag = NBTByteArray.fromByteArray(in, true);
            else if (type == NBTType.STRING.ordinal()) tag = NBTString.fromByteArray(in, true);
            else if (type == NBTType.LIST.ordinal()) tag = NBTList.fromByteArray(in, true);
            else if (type == NBTType.COMPOUND.ordinal()) tag = NBTCompound.fromByteArray(in, true);
            else if (type == NBTType.INT_ARRAY.ordinal()) tag = NBTIntArray.fromByteArray(in, true);
            else if (type == NBTType.LONG_ARRAY.ordinal()) tag = NBTLongArray.fromByteArray(in, true);

            if (tag != null) {
                compound.data.put(tag.name, tag);
            }
        }

        return compound;
    }

    /**
     * Build an optionally named byte array from the tag
     * @param out The byte array to write to
     * @param named Whether or not the tag is named
     */
    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if (named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }

        for (NBTTag tag : this.data.values()) {
            out.writeByte(tag.type.ordinal());
            tag.toByteArray(out, true);
        }

        out.writeByte(0);
    }
}
