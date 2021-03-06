package dev.floffah.gamermode.world.enums;

public enum BaseWorldType {
    OVERWORLD("minecraft:overworld"),
    NETHER("minecraft:the_nether"),
    END("minecraft:the_end");

    public final String name;

    BaseWorldType(String name) {
        this.name = name;
    }
}
