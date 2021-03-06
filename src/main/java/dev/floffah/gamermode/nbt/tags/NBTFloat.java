package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.charset.StandardCharsets;

public class NBTFloat extends NBTTag {
    public float value;

    public NBTFloat() {
        super(NBTType.FLOAT);
    }

    public static NBTFloat quick(String name, float value) {
        NBTFloat i = new NBTFloat();
        i.name = name;
        i.value = value;
        return i;
    }

    public static void compound(NBTCompound c, String name, float value) {
        NBTFloat str = quick(name, value);
        c.data.put(str.name, str);
    }

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

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if (named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }
        out.writeFloat(this.value);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}