package dev.floffah.gamermode.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * ComponentBuilder inspired by md_5s implementation
 */
public class ComponentBuilder {
    /**
     * The extra components (incomplete)
     * -- GETTER --
     * Get the raw extra components (incomplete)
     *
     * @return The extra components (incomplete)
     */
    @Getter
    List<Component> extra = new ArrayList<>();
    /**
     * The raw component (incomplete)
     * -- GETTER --
     * Get the raw component (incomplete)
     *
     * @return The raw component (incomplete)
     */
    @Getter
    Component component;
    /**
     * The cursor location
     * -- GETTER --
     * Get the current cursor location<br/>
     * The cursor indicates what component the builder is currently editing.<br/>
     * -1 being the raw component<br/>
     * >=0 being the index in the extra list
     *
     * @return The cursor location
     * -- SETTER --
     * Set the current cursor location<br/>
     * See {@link ComponentBuilder#getCursor()} for details
     * @param cursor The cursor location
     */
    @Getter
    @Setter
    int cursor = -1;

    /**
     * Instantiate a component builder with text on the root component
     *
     * @param text The text to use (can be null, but you must set the text using {@link ComponentBuilder#text(String)} )
     */
    public ComponentBuilder(String text) {
        component = new Component(text);
    }

    /**
     * Set the current component's text
     *
     * @param text The text to set
     * @return This {@link ComponentBuilder}
     */
    public ComponentBuilder text(String text) {
        getCurrent().text = text;
        return this;
    }

    /**
     * Set the current component's color
     *
     * @param color The {@link ChatColor} to set
     * @return
     */
    public ComponentBuilder color(ChatColor color) {
        getCurrent().color = color;
        return this;
    }

    /**
     * Set whether the current component is obfuscated
     *
     * @param val State to set
     * @return This {@link ComponentBuilder}
     */
    public ComponentBuilder obfuscated(boolean val) {
        getCurrent().obfuscated = val;
        return this;
    }

    /**
     * Set whether the current component is bold
     *
     * @param val State to set
     * @return This {@link ComponentBuilder}
     */
    public ComponentBuilder bold(boolean val) {
        getCurrent().bold = val;
        return this;
    }

    /**
     * Set whether the current component has a strike through it
     *
     * @param val State to set
     * @return This {@link ComponentBuilder}
     */
    public ComponentBuilder strikethrough(boolean val) {
        getCurrent().strikethrough = val;
        return this;
    }

    /**
     * Set whether the current component is underlined
     *
     * @param val State to set
     * @return This {@link ComponentBuilder}
     */
    public ComponentBuilder underline(boolean val) {
        getCurrent().underlined = val;
        return this;
    }

    /**
     * Set whether the current component is italic
     *
     * @param val State to set
     * @return This {@link ComponentBuilder}
     */
    public ComponentBuilder italic(boolean val) {
        getCurrent().italic = val;
        return this;
    }

    /**
     * Add a new extra {@link Component} with specified text
     *
     * @param text The text to give the {@link Component}
     * @return this {@link ComponentBuilder} with the cursor on the new {@link Component}
     */
    public ComponentBuilder append(String text) {
        cursor++;
        extra.add(new Component(text));
        return this;
    }

    /**
     * Get the current {@link Component}
     *
     * @return The current {@link Component}
     */
    Component getCurrent() {
        if (cursor == -1) return component;
        else return extra.get(cursor);
    }

    /**
     * Build the {@link ComponentBuilder} into a {@link Component}
     *
     * @return The built {@link Component}
     */
    public Component build() {
        component.extra = extra;
        return component;
    }
}
