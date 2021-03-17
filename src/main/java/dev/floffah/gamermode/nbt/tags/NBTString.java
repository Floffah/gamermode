package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

public class NBTString extends NBTTag {
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
    public String value;

    /**
     * Construct an NBTString
     */
    public NBTString() {
        super(NBTType.STRING);
    }

    /**
     * Create an NBTByte from an optionally named byte array
     * @param in Byte array
     * @param named Whether or not the tag is named
     * @return The constructed string
     */
    public static NBTString fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTString nstr = new NBTString();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nstr.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        short vallen = in.readShort();
        byte[] valbytes = new byte[vallen];
        for (int i = 0; i < vallen; i++) {
            valbytes[i] = in.readByte();
        }

        nstr.value = new String(valbytes, StandardCharsets.UTF_8);

        return nstr;
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

        byte[] bs = this.value.getBytes(StandardCharsets.UTF_8);

        out.writeShort(bs.length);
        for (byte b : bs) {
            out.writeByte(b);
        }
    }
}