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
     * -- SETTER --
     * Set the component's text
     */
    @Getter
    @Setter
    String text;
    /**
     * The current component's color
     * -- GETTER --
     * Get the current component's color
     * -- SETTER --
     * Set the component's color
     *
     * @param color The color to set
     */
    @Getter
    @Setter
    ChatColor color;

    /**
     * Whether or not the component is obfuscated
     * -- GETTER --
     * Get whether or not the component is obfuscated
     * -- SETTER --
     * Set the component's obfuscation state
     *
     * @param obfuscated Obfuscation state
     */
    @Getter
    @Setter
    boolean obfuscated;
    /**
     * Whether or not the component is bold
     * -- GETTER --
     * Get whether or not the component is bold
     * -- SETTER --
     * Set the component's bold state
     *
     * @param bold Bold state
     */
    @Getter
    @Setter
    boolean bold;
    /**
     * Whether or not the component has a strike through it
     * -- GETTER --
     * Get whether or not the component has a strike through it
     * -- SETTER --
     * Set the component's strikethrough state
     *
     * @param strikethrough Strikethrough state
     */
    @Getter
    @Setter
    boolean strikethrough;
    /**
     * Whether or not the component is underlined
     * -- GETTER --
     * Get whether or not the component is underlined
     * -- SETTER --
     * Set the component's underline state
     *
     * @param underlined Underline state
     */
    @Getter
    @Setter
    boolean underlined;
    /**
     * Whether or not the component is italic
     * -- GETTER --
     * Get whether or not the component is italic
     * -- SETTER --
     * Set the component's italic state
     *
     * @param italic Italics state
     */
    @Getter
    @Setter
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
