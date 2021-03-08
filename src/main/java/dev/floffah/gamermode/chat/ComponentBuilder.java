package dev.floffah.gamermode.chat;

import java.util.ArrayList;
import java.util.List;

/**
 * ComponentBuilder inspired by md_5s implementation
 */
public class ComponentBuilder {
    List<Component> extra = new ArrayList<>();
    Component component;
    int cursor = -1;

    public ComponentBuilder(String text) {
        component = new Component(text);
    }

    public ComponentBuilder color(ChatColor color) {
        getCurrent().color = color;
        return this;
    }

    public ComponentBuilder obfuscated(boolean val) {
        getCurrent().obfuscated = val;
        return this;
    }

    public ComponentBuilder bold(boolean val) {
        getCurrent().bold = val;
        return this;
    }

    public ComponentBuilder strikethrough(boolean val) {
        getCurrent().strikethrough = val;
        return this;
    }

    public ComponentBuilder underline(boolean val) {
        getCurrent().underlined = val;
        return this;
    }

    public ComponentBuilder italic(boolean val) {
        getCurrent().italic = val;
        return this;
    }

    public ComponentBuilder append(String text) {
        cursor++;
        extra.add(new Component(text));
        return this;
    }

    Component getCurrent() {
        if (cursor == -1) return component;
        else return extra.get(cursor);
    }

    public Component build() {
        component.extra = extra;
        return component;
    }
}
