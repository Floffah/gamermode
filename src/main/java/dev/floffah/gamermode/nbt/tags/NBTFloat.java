package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;

import java.nio.charset.StandardCharsets;

public class NBTFloat extends NBTTag {
    public float value;
    public String name;

    public NBTFloat() {
        super(NBTType.FLOAT);
    }

    public static NBTFloat fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTFloat nfloat = new NBTFloat();

        if(named) {
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

    public static NBTFloat fromByteArray(ByteArrayDataInput in) {
        return fromByteArray(in, true);
    }
}