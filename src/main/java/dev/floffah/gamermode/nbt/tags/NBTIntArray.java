package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

public class NBTIntArray extends NBTTag {
    /**
     * The tag's value
     * -- GETTER --
     * Get the tag's value
     * @return The tags value
     * -- SETTER --
     * Set the tag's value
     * @param value The new value
     */
    @Getter
    @Setter
    public int[] value;

    /**
     * Construct an NBTIntArray
     */
    public NBTIntArray() {
        super(NBTType.INT_ARRAY);
    }

    /**
     * Create an NBTIntArray from an optionally named byte array
     * @param in Byte array
     * @param named Whether or not the tag is named
     * @return The constructed byte
     */
    public static NBTIntArray fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTIntArray nintarr = new NBTIntArray();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nintarr.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        int intlen = in.readInt();
        int[] intarr = new int[intlen];

        for (int i = 0; i < intlen; i++) {
            intarr[i] = in.readInt();
        }

        nintarr.value = intarr;

        return nintarr;
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

        out.writeInt(this.value.length);

        for (int i = 0; i < this.value.length; i++) {
            out.writeInt(this.value[i]);
        }
    }
}