package dev.floffah.gamermode.nbt.tags;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.errorprone.annotations.ForOverride;
import lombok.Getter;
import lombok.Setter;

public abstract class NBTTag {
    /**
     * The tag type
     * -- GETTER --
     * Get the tag type
     *
     * @return The tag type
     */
    @Getter
    public NBTType type;
    /**
     * The tag name
     * -- GETTER --
     * Get the tag name
     * @return The tag name
     * -- SETTER --
     * Set the tag name
     * @param name The new tag name
     */
    @Getter
    @Setter
    public String name;

    /**
     * Constructor for extending the NBTTag
     * @param type Type of the tag
     */
    public NBTTag(NBTType type) {
        this.type = type;
    }

    /**
     * Create an NBTTag from an optionally named byte array
     * @param in Byte array
     * @param named Whether or not the tag is named
     * @return The constructed tag
     */
    @ForOverride
    public static NBTTag fromByteArray(ByteArrayDataInput in, boolean named) {
        return null;
    }

    /**
     * Build an optionally named byte array from the tag
     * @param out The byte array to write to
     * @param named Whether or not the tag is named
     */
    @ForOverride
    public abstract void toByteArray(ByteArrayDataOutput out, boolean named);
}
