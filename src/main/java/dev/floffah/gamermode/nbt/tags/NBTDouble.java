package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.charset.StandardCharsets;

public class NBTDouble extends NBTTag {
    public double value;
    public String name;

    public NBTDouble() {
        super(NBTType.DOUBLE);
    }

    public static NBTDouble fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTDouble ndouble = new NBTDouble();

        if(named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            ndouble.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        ndouble.value = in.readDouble();

        return ndouble;
    }

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if(named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }
        out.writeDouble(this.value);
    }
}