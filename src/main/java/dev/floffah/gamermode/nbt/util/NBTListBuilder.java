package dev.floffah.gamermode.nbt.util;

import dev.floffah.gamermode.nbt.tags.NBTList;
import dev.floffah.gamermode.nbt.tags.NBTTag;
import dev.floffah.gamermode.nbt.tags.NBTType;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * An NBT builder class for building nbt lists. Iterable because why not
 */
public class NBTListBuilder implements Iterable<NBTTag> {
    /**
     * The list's name
     * -- GETTER --
     * Get the list's name
     *
     * @return The list's name
     * -- SETTER --
     * Set the list's name
     * @param name The new name
     */
    @Getter
    @Setter
    public String name;
    NBTCompoundBuilder parent;
    NBTList lst;
    NBTType type;

    /**
     * Create a new NBT list.<br/>
     * Always in parent mode as it cannot be the root tag of an NBT object
     *
     * @param parent The parent compound builder
     * @param name   The list name
     * @param type   The type of elements in the list
     */
    public NBTListBuilder(NBTCompoundBuilder parent, String name, NBTType type) {
        this.parent = parent;
        this.name = name;
        this.type = type;
        lst = new NBTList();
        lst.name = name;
        lst.valtype = type;
    }

    /**
     * Put a tag into the NBT list
     *
     * @param tag The tag to put
     * @return This NBT list builder
     */
    public NBTListBuilder put(NBTTag tag) {
        if (tag.type != type) {
            return null;
        }
        lst.value.add(lst);
        return this;
    }

    /**
     * Build the NBT list and get the parent compound builder
     *
     * @return The parent compound builder
     */
    public NBTCompoundBuilder build() {
        parent.cmp.data.put(lst.name, lst);
        return parent;
    }

    /**
     * Returns an iterator over elements of type {@link NBTTag}.
     *
     * @return The Iterator.
     */
    @NotNull
    @Override
    public Iterator<NBTTag> iterator() {
        return lst.value.iterator();
    }
}
