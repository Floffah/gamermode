package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

public class NBTByteArray extends NBTTag {
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
    public byte[] value;

    /**
     * Construct an NBTByteArray
     */
    public NBTByteArray() {
        super(NBTType.BYTE_ARRAY);
    }

    /**
     * Create an NBTByteArray from an optionally named byte array
     * @param in Byte array to use
     * @param named Whether or not the tag is named
     * @return the constructed byte array
     */
    public static NBTByteArray fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTByteArray nbytearr = new NBTByteArray();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nbytearr.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        int bytelen = in.readInt();
        byte[] bytearr = new byte[bytelen];

        for (int i = 0; i < bytelen; i++) {
            bytearr[i] = in.readByte();
        }

        nbytearr.value = bytearr;

        return nbytearr;
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
        for (byte b : value) {
            out.write(b);
        }
    }
}