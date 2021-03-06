package dev.floffah.gamermode.nbt.util;

import dev.floffah.gamermode.nbt.NBTObject;

public class NBTBuilder {
    NBTObject obj;
    NBTCompoundBuilder root;

    public static NBTCompoundBuilder start(String name) {
        NBTBuilder builder = new NBTBuilder();
        return builder.startRoot(name);
    }

    /**
     * Start root nbt compound builder.
     * Must run end on the root compound builder. do not use build or you will get null pointers
     * name can be an empty string ""
     * null name will be converted to empty string ""
     *
     * @return the nbt compound builder
     */
    public NBTCompoundBuilder startRoot(String name) {
        if (name == null) name = "";
        root = new NBTCompoundBuilder(this, name);
        return root;
    }

    public NBTObject build() {
        obj.setRoot(root.cmp);
        root = null;
        return obj;
    }
}
