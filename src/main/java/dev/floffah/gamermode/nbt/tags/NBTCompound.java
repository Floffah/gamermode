package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class NBTCompound extends NBTTag {
    public HashMap<String, NBTTag> data = new HashMap<>();

    public NBTCompound() {
        super(NBTType.COMPOUND);
    }

    @NotNull
    public static NBTCompound fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTCompound compound = new NBTCompound();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }
            compound.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        for (; ; ) {
            byte type = in.readByte();

            NBTTag tag = null;

            if (type == NBTType.END.ordinal()) break;
            else if (type == NBTType.BYTE.ordinal()) tag = NBTByte.fromByteArray(in, true);
            else if (type == NBTType.SHORT.ordinal()) tag = NBTShort.fromByteArray(in, true);
            else if (type == NBTType.INT.ordinal()) tag = NBTInt.fromByteArray(in, true);
            else if (type == NBTType.LONG.ordinal()) tag = NBTLong.fromByteArray(in, true);
            else if (type == NBTType.FLOAT.ordinal()) tag = NBTFloat.fromByteArray(in, true);
            else if (type == NBTType.DOUBLE.ordinal()) tag = NBTDouble.fromByteArray(in, true);
            else if (type == NBTType.BYTE_ARRAY.ordinal()) tag = NBTByteArray.fromByteArray(in, true);
            else if (type == NBTType.STRING.ordinal()) tag = NBTString.fromByteArray(in, true);
            else if (type == NBTType.LIST.ordinal()) tag = NBTList.fromByteArray(in, true);
            else if (type == NBTType.COMPOUND.ordinal()) tag = NBTCompound.fromByteArray(in, true);
            else if (type == NBTType.INT_ARRAY.ordinal()) tag = NBTIntArray.fromByteArray(in, true);
            else if (type == NBTType.LONG_ARRAY.ordinal()) tag = NBTLongArray.fromByteArray(in,true);

            if (tag != null) {
                compound.data.put(tag.name, tag);
            }
        }

        return compound;
    }

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if(named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }

        for (String s : this.data.keySet()) {
            NBTTag tag = this.data.get(s);
            out.writeByte(tag.type.ordinal());
            tag.toByteArray(out, true);
        }

        out.writeByte(0);
    }
}
