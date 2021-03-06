package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.charset.StandardCharsets;

public class NBTByte extends NBTTag {
    public byte value;

    public NBTByte() {
        super(NBTType.BYTE);
    }

    public static NBTByte quick(String name, byte value) {
        NBTByte i = new NBTByte();
        i.name = name;
        i.value = value;
        return i;
    }

    public static void compound(NBTCompound c, String name, byte value) {
        NBTByte str = quick(name, value);
        c.data.put(str.name, str);
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

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if (named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }
        out.writeByte(value);
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
