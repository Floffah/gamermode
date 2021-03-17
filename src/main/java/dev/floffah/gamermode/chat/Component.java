package dev.floffah.gamermode.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * BaseComponent class inspired by md_5s implementation
 */
public class Component {
    /**
     * The extra components
     * -- GETTER --
     * Get the extra components
     */
    @Getter
    List<Component> extra;

    /**
     * The text of the current component
     * -- GETTER --
     * Get the current component's text
     *
     * @return The current component's text
     */
    @Getter
    String text;
    /**
     * The current component's color
     * -- GETTER --
     * Get the current component's color
     *
     * @return The current component's color
     */
    @Getter
    ChatColor color;

    /**
     * Whether or not the component is obfuscated
     * -- GETTER --
     * Get whether or not the component is obfuscated
     *
     * @return The obfuscation state
     */
    @Getter
    boolean obfuscated;
    /**
     * Whether or not the component is bold
     * -- GETTER --
     * Get whether or not the component is bold
     *
     * @return Whether or not the component is bold
     */
    @Getter
    boolean bold;
    /**
     * Whether or not the component has a strike through it
     * -- GETTER --
     * Get whether or not the component has a strike through it
     *
     * @return Whether or not the component has a strike through it
     */
    @Getter
    boolean strikethrough;
    /**
     * Whether or not the component is underlined
     * -- GETTER --
     * Get whether or not the component is underlined
     *
     * @return Whether or not the component is underlined
     */
    @Getter
    boolean underlined;
    /**
     * Whether or not the component is italic
     * -- GETTER --
     * Get whether or not the component is italic
     *
     * @return Whether or not the component is italic
     */
    @Getter
    boolean italic;

    /**
     * Construct a component
     *
     * @param text The text to initialise with
     */
    public Component(String text) {
        this.text = text;
    }

    /**
     * Convert the component into a json string
     *
     * @return The component as a json string
     */
    public String toString() {
        return new ComponentSerializer(this).toString();
    }
}
