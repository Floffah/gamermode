package dev.floffah.gamermode.chat;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A helper class used to serialize a built {@link Component} into json (chat ready) form.
 */
public class ComponentSerializer {
    /**
     * The built {@link Component} used to serialize.
     * -- GETTER --
     * Get the built {@link Component} being used to serialize.
     */
    @Getter
    Component component;

    /**
     * Initialize a ComponentSerializer for serializing a {@link Component}
     * @param component The built {@link Component}
     */
    public ComponentSerializer(Component component) {
        this.component = component;
    }

    /**
     * Convert the component to a passed {@link JSONObject} object
     * @param json The {@link JSONObject} to use
     * @param c The {@link Component} to convert
     */
    void put(JSONObject json, Component c) {
        json.put("text", c.text);
        if (c.bold) json.put("bold", "true");
        if (c.italic) json.put("italic", "true");
        if (c.underlined) json.put("underlined", "true");
        if (c.strikethrough) json.put("strikethrough", "true");
        if (c.obfuscated) json.put("obfuscated", "true");
        if (c.color != null) json.put("color", c.color.name);
    }

    /**
     * Convert the {@link Component} to a JSON string
     * @return The {@link Component} as a JSON string
     */
    @Override
    public String toString() {
        JSONObject json = new JSONObject();

        put(json, component);

        if (component.extra != null && component.extra.size() >= 1) {
            JSONArray extras = new JSONArray();

            for (Component cmp : component.extra) {
                JSONObject j = new JSONObject();
                put(j, cmp);
                extras.put(j);
            }

            json.put("extras", extras);
        }

        return json.toString();
    }
}
