package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;

import java.nio.charset.StandardCharsets;

public class NBTByteArray extends NBTTag {
    public byte[] value;
    public String name;

    public NBTByteArray() {
        super(NBTType.BYTE_ARRAY);
    }

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

    public static NBTByteArray fromByteArray(ByteArrayDataInput in) {
        return fromByteArray(in, true);
    }
}