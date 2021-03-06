package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.charset.StandardCharsets;

public class NBTInt extends NBTTag {
    public int value;

    public NBTInt() {
        super(NBTType.INT);
    }

    public static NBTInt quick(String name, int value) {
        NBTInt i = new NBTInt();
        i.name = name;
        i.value = value;
        return i;
    }

    public static void compound(NBTCompound c, String name, int value) {
        NBTInt str = quick(name, value);
        c.data.put(str.name, str);
    }

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

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if (named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }
        out.writeInt(this.value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}