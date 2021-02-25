package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class NBTByte extends NBTTag {
    public byte value;
    public String name;

    public NBTByte() {
        super(NBTType.BYTE);
    }

    public static NBTByte fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTByte nbyte = new NBTByte();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nbyte.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        nbyte.value = in.readByte();

        return nbyte;
    }

    public static NBTByte fromByteArray(ByteArrayDataInput in) {
        return fromByteArray(in, true);
    }
}
