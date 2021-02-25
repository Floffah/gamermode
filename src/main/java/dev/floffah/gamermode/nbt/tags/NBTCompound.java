package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class NBTCompound extends NBTTag {
    public HashMap<String, NBTTag> data = new HashMap<>();
    public String name;

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
            byte type;

            try {
                type = in.readByte();
            } catch (Exception e) {
                break;
            }

            NBTTag tag = null;

            if (type == NBTType.END.ordinal()) break;
            else if (type == NBTType.BYTE.ordinal()) tag = NBTByte.fromByteArray(in);
            else if (type == NBTType.SHORT.ordinal()) tag = NBTShort.fromByteArray(in);
            else if (type == NBTType.INT.ordinal()) tag = NBTInt.fromByteArray(in);
            else if (type == NBTType.LONG.ordinal()) tag = NBTLong.fromByteArray(in);
            else if (type == NBTType.FLOAT.ordinal()) tag = NBTFloat.fromByteArray(in);
            else if (type == NBTType.DOUBLE.ordinal()) tag = NBTDouble.fromByteArray(in);
            else if (type == NBTType.BYTE_ARRAY.ordinal()) tag = NBTByteArray.fromByteArray(in);
            else if (type == NBTType.STRING.ordinal()) tag = NBTString.fromByteArray(in);
            else if (type == NBTType.LIST.ordinal()) tag = NBTList.fromByteArray(in);
            else if (type == NBTType.COMPOUND.ordinal()) tag = NBTCompound.fromByteArray(in);
            else if (type == NBTType.INT_ARRAY.ordinal()) tag = NBTIntArray.fromByteArray(in);
            else if (type == NBTType.LONG_ARRAY.ordinal()) tag = NBTLongArray.fromByteArray(in);

            if (tag != null) {
                compound.data.put(tag.name, tag);
            }
        }

        return compound;
    }

    public static NBTCompound fromByteArray(ByteArrayDataInput in) {
        return fromByteArray(in, true);
    }
}
