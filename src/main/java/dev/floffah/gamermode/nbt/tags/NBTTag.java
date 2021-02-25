package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.errorprone.annotations.ForOverride;

public class NBTTag {
    NBTType type;
    public String name = null;

    public NBTTag(NBTType type) {
        this.type = type;
    }

    @ForOverride
    public static NBTTag fromByteArray(ByteArrayDataInput in, boolean named) {
        return null;
    }

    @ForOverride
    public static NBTTag fromByteArray(ByteArrayDataInput in) {
        return null;
    }
}
