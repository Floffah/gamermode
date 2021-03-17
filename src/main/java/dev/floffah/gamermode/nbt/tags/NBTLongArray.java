package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

public class NBTLongArray extends NBTTag {
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
    public long[] value;

    /**
     * Construct an NBTLongArray
     */
    public NBTLongArray() {
        super(NBTType.BYTE_ARRAY);
    }

    /**
     * Create an NBTLongArray from an optionally named byte array
     * @param in Byte array
     * @param named Whether or not the tag is named
     * @return The constructed long array
     */
    public static NBTLongArray fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTLongArray nlongarr = new NBTLongArray();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nlongarr.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        int longlen = in.readInt();
        long[] longarr = new long[longlen];

        for (int i = 0; i < longlen; i++) {
            longarr[i] = in.readLong();
        }

        nlongarr.value = longarr;

        return nlongarr;
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
        out.writeInt(value.length);
        for (long l : value) {
            out.writeLong(l);
        }
    }
}