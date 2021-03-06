package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.errorprone.annotations.ForOverride;

public abstract class NBTTag {
    public NBTType type;
    public String name;

    public NBTTag(NBTType type) {
        this.type = type;
    }

    @ForOverride
    public static NBTTag fromByteArray(ByteArrayDataInput in, boolean named) {
        return null;
    }

    @ForOverride
    public abstract void toByteArray(ByteArrayDataOutput out, boolean named);
}
