package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.charset.StandardCharsets;

public class NBTString extends NBTTag {
    public String value;
    public String name;

    public NBTString() {
        super(NBTType.STRING);
    }

    public static NBTString fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTString nstr = new NBTString();

        if(named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nstr.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        short vallen = in.readShort();
        byte[] valbytes = new byte[vallen];
        for (int i = 0; i < vallen; i++) {
            valbytes[i] = in.readByte();
        }

        nstr.value = new String(valbytes, StandardCharsets.UTF_8);

        return nstr;
    }

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if(named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }

        byte[] bs = this.value.getBytes(StandardCharsets.UTF_8);

        out.writeShort(bs.length);
        for (byte b : bs) {
            out.writeByte(b);
        }
    }
}