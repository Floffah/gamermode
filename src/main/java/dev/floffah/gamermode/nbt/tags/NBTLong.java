package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;

import java.nio.charset.StandardCharsets;

public class NBTLong extends NBTTag {
    public long value;
    public String name;

    public NBTLong() {
        super(NBTType.LONG);
    }

    public static NBTLong fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTLong nlong = new NBTLong();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nlong.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        nlong.value = in.readLong();

        return nlong;
    }

    public static NBTLong fromByteArray(ByteArrayDataInput in) {
        return fromByteArray(in, true);
    }
}