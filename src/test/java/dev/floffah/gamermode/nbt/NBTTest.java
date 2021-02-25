package dev.floffah.gamermode.nbt;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class NBTTest {
    public NBTObject nbt = new NBTObject();

    @Test
    @DisplayName("Read and write a copy test using wiki.vg bigtest")
    @Order(1)
    public void bigtestread() throws IOException {
        InputStream inp = this.getClass().getResourceAsStream("/bigtest.nbt");
        DataInputStream in = new DataInputStream(inp);
        nbt.read(in);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        nbt.write(out);
        File outf = new File("bigtest.copy.nbt");
        System.out.println(outf.getAbsolutePath());
        byte[] outb = out.toByteArray();
        FileUtils.writeByteArrayToFile(outf, outb);
        Assertions.assertTrue(outb.length > 0);
    }

//    @Test
//    @DisplayName("Write read nbt as new file to compare to original bigtest")
//    @Order(2)
//    public void bigtestwrite() throws IOException {
//        ByteArrayDataOutput out = ByteStreams.newDataOutput();
//        nbt.write(out);
//        File outf = new File("/bigtest.copy.nbt");
//        byte[] outb = out.toByteArray();
//        FileUtils.writeByteArrayToFile(outf, outb);
//        Assertions.assertTrue(outb.length > 0);
//    }
}
