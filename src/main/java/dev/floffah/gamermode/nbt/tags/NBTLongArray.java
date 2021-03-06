package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.charset.StandardCharsets;

public class NBTLongArray extends NBTTag {
    public long[] value;

    public NBTLongArray() {
        super(NBTType.BYTE_ARRAY);
    }

    public static NBTLongArray fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTLongArray nlongarr = new NBTLongArray();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nlongarr.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        int longlen = in.readInt();
        long[] longarr = new long[longlen];

        for (int i = 0; i < longlen; i++) {
            longarr[i] = in.readLong();
        }

        nlongarr.value = longarr;

        return nlongarr;
    }

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if (named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }
        out.writeInt(value.length);
        for (long l : value) {
            out.writeLong(l);
        }
    }

    public long[] getValue() {
        return value;
    }

    public void setValue(long[] value) {
        this.value = value;
    }
}