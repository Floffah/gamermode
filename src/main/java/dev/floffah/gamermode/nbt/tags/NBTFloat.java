package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

public class NBTFloat extends NBTTag {
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
    public float value;

    /**
     * Construct an NBTFloat
     */
    public NBTFloat() {
        super(NBTType.FLOAT);
    }

    /**
     * Create an NBTFloat from an optionally named byte array
     * @param in Byte array
     * @param named Whether or not the tag is named
     * @return The constructed byte
     */
    public static NBTFloat fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTFloat nfloat = new NBTFloat();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nfloat.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        nfloat.value = in.readFloat();

        return nfloat;
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
        out.writeFloat(this.value);
    }
}