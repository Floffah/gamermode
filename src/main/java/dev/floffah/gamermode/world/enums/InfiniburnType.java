package dev.floffah.gamermode.world.enums;

public enum InfiniburnType {
    OVERWORLD("minecraft:infiniburn_overworld"),
    NETHER("minecraft:infiniburn_nether"),
    END("minecraft:infiniburn_end");

    public final String name;

    InfiniburnType(String name) {
        this.name = name;
    }
}
