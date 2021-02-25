package dev.floffah.gamermode.nbt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NBTTest {
    private NBTObject nbt;

    @BeforeEach
    public void setup() {
        nbt = new NBTObject();
    }

    @Test
    @DisplayName("Read test using wiki.vg bigtest")
    public void bigtest() throws IOException {
        InputStream inp = this.getClass().getResourceAsStream("/bigtest.nbt");
        DataInputStream in = new DataInputStream(inp);
        nbt.read(in);
        Assertions.assertNotNull(nbt.data.firstEntry());
    }
}
