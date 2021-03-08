package dev.floffah.gamermode.chat;

import java.util.List;

/**
 * BaseComponent class inspired by md_5s implementation
 */
public class Component {
    List<Component> extra;
    //BaseComponent host = null;

    String text;
    ChatColor color;

    boolean obfuscated;
    boolean bold;
    boolean strikethrough;
    boolean underlined;
    boolean italic;

    public Component(String text) {
        this.text = text;
    }

    public String toString() {
        return new ComponentSerializer(this).toString();
    }

//    public BaseComponent(BaseComponent host) {
//        this.host = host;
//    }
//
//    public BaseComponent() {
//        extra = new HashSet<>();
//    }
//
    //public void addExtra(BaseComponent extra) {
    //Objects.requireNonNullElse(host, this).extra.add(extra);
    //}
}
