package dev.floffah.gamermode.chat;

import org.json.JSONArray;
import org.json.JSONObject;

public class ComponentSerializer {
    Component component;

    public ComponentSerializer(Component component) {
        this.component = component;
    }

    void put(JSONObject json, Component c) {
        json.put("text", c.text);
        if (c.bold) json.put("bold", "true");
        if (c.italic) json.put("italic", "true");
        if (c.underlined) json.put("underlined", "true");
        if (c.strikethrough) json.put("strikethrough", "true");
        if (c.obfuscated) json.put("obfuscated", "true");
        if(c.color != null) json.put("color", c.color.name);
    }

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
