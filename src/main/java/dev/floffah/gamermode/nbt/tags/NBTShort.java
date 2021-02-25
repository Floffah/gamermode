package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;

import java.nio.charset.StandardCharsets;

public class NBTShort extends NBTTag {
    public short value;
    public String name;

    public NBTShort() {
        super(NBTType.SHORT);
    }

    public static NBTShort fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTShort nshort = new NBTShort();

        if(named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nshort.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        nshort.value = in.readShort();

        return nshort;
    }

    public static NBTShort fromByteArray(ByteArrayDataInput in) {
        return fromByteArray(in, true);
    }
}
