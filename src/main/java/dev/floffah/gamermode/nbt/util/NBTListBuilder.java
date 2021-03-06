package dev.floffah.gamermode.nbt.util;

import dev.floffah.gamermode.nbt.tags.NBTList;
import dev.floffah.gamermode.nbt.tags.NBTTag;
import dev.floffah.gamermode.nbt.tags.NBTType;

public class NBTListBuilder {
    public String name;
    NBTCompoundBuilder parent;
    NBTList lst;
    NBTType type;

    public NBTListBuilder(NBTCompoundBuilder parent, String name, NBTType type) {
        this.parent = parent;
        this.name = name;
        this.type = type;
        lst = new NBTList();
        lst.name = name;
    }

    public NBTListBuilder put(NBTTag tag) {
        if (tag.type != type) {
            return null;
        }
        lst.value.add(lst);
        return this;
    }

    public NBTCompoundBuilder build() {
        parent.cmp.data.put(lst.name, lst);
        return parent;
    }
}
