package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class NBTList extends NBTTag {
    public Set<NBTTag> value = new HashSet<>();
    public NBTType valtype;
    public int vallen;

    public NBTList() {
        super(NBTType.LIST);
    }

    public static NBTList fromByteArray(ByteArrayDataInput in, boolean named) {
        NBTList nlist = new NBTList();

        if (named) {
            short namelen = in.readShort();
            byte[] namebytes = new byte[namelen];
            for (int i = 0; i < namelen; i++) {
                namebytes[i] = in.readByte();
            }

            nlist.name = new String(namebytes, StandardCharsets.UTF_8);
        }

        byte type = in.readByte();
        nlist.valtype = NBTType.values()[type];

        int len = in.readInt();
        nlist.vallen = len;

        nlist.value.clear();

        Class<?> typecl = NBTTag.class;

        if (type == NBTType.BYTE.ordinal()) typecl = NBTByte.class;
        else if (type == NBTType.SHORT.ordinal()) typecl = NBTShort.class;
        else if (type == NBTType.INT.ordinal()) typecl = NBTInt.class;
        else if (type == NBTType.LONG.ordinal()) typecl = NBTLong.class;
        else if (type == NBTType.FLOAT.ordinal()) typecl = NBTFloat.class;
        else if (type == NBTType.DOUBLE.ordinal()) typecl = NBTDouble.class;
        else if (type == NBTType.BYTE_ARRAY.ordinal()) typecl = NBTByteArray.class;
        else if (type == NBTType.STRING.ordinal()) typecl = NBTString.class;
        else if (type == NBTType.LIST.ordinal()) typecl = NBTList.class;
        else if (type == NBTType.COMPOUND.ordinal()) typecl = NBTCompound.class;
        else if(type == NBTType.INT_ARRAY.ordinal()) typecl = NBTIntArray.class;
        else if(type == NBTType.LONG_ARRAY.ordinal()) typecl = NBTLongArray.class;

        Method invoker = null;
        try {
            invoker = typecl.getMethod("fromByteArray", ByteArrayDataInput.class, boolean.class);
        } catch (NoSuchMethodException e) {
        }

        if (invoker == null) return nlist;

        for (int i = 0; i < len; i++) {
            NBTTag tag = null;
            try {
                tag = (NBTTag) invoker.invoke(null, in, false);
            } catch (IllegalAccessException | InvocationTargetException e) {
                continue;
            }
            nlist.value.add(tag);
        }

        return nlist;
    }

    @Override
    public void toByteArray(ByteArrayDataOutput out, boolean named) {
        if(named) {
            byte[] b = this.name.getBytes(StandardCharsets.UTF_8);
            out.writeShort(b.length);
            out.write(b);
        }

        out.writeByte(this.valtype.ordinal());
        out.writeInt(this.value.size());

        for (NBTTag tag : this.value) {
            tag.toByteArray(out, false);
        }
    }
}