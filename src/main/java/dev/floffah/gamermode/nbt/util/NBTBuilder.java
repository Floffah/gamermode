package dev.floffah.gamermode.nbt.util;

import dev.floffah.gamermode.nbt.NBTObject;

/**
 * The main NBT builder class that should be the entrypoint for building NBT
 */
public class NBTBuilder {
    NBTObject obj;
    NBTCompoundBuilder root;

    /**
     * Start an NBTCompound and automatically create a root compound with the name
     * @param name The name to initialise with
     * @return The NBTCompoundBuilder made
     */
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

    /**
     * Build the NBTBuilder to a NBTObject
     * @return The NBTObject built
     */
    public NBTObject build() {
        obj = new NBTObject();
        obj.setRoot(root.cmp);
        root = null;
        return obj;
    }
}
