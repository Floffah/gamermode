package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

public class NBTInt extends NBTTag {
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
    public int value;

    /**
     * Construct an NBTInt
     */
    public NBTInt() {
        super(NBTType.INT);
    }

    /**
     * Create an NBTInt from an optionally named byte array
     * @param in Byte array
     * @param named Whether or not the tag is named
     * @return The constructed int
     */
    public static NBTInt fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTInt nint = new NBTInt();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nint.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        nint.value = in.readInt();

        return nint;
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
        out.writeInt(this.value);
    }
}