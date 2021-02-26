package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.charset.StandardCharsets;

public class NBTIntArray extends NBTTag {
    public int[] value;

    public NBTIntArray() {
        super(NBTType.INT_ARRAY);
    }

    public static NBTIntArray fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTIntArray nintarr = new NBTIntArray();

        if(named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nintarr.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        int intlen = in.readInt();
        int[] intarr = new int[intlen];

        for (int i = 0; i < intlen; i++) {
            intarr[i] = in.readInt();
        }

        nintarr.value = intarr;

        return nintarr;
    }

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if(named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }

        out.writeInt(this.value.length);

        for (int i = 0; i < this.value.length; i++) {
            out.writeInt(this.value[i]);
        }
    }
}