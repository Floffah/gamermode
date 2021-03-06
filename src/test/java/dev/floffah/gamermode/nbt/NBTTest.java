package dev.floffah.gamermode.nbt;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class NBTTest {
    public NBTObject nbt;

    @Test
    @DisplayName("Read and write a copy test using wiki.vg bigtest")
    @Order(1)
    public void bigtest() throws IOException {
        nbt = new NBTObject();
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

    @Test
    @DisplayName("Read gzipped and write uncompressed copy test using wiki.vg bigtest")
    @Order(2)
    public void gzipbigtest() throws IOException {
        nbt = new NBTObject();
        InputStream inp = this.getClass().getResourceAsStream("/bigtest.nbt.gz");
        DataInputStream in = new DataInputStream(inp);
        nbt.read(in, true);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        nbt.write(out);
        File outf = new File("bigtest.copy.ungz.nbt");
        System.out.println(outf.getAbsolutePath());
        byte[] outb = out.toByteArray();
        FileUtils.writeByteArrayToFile(outf, outb);
        Assertions.assertTrue(outb.length > 0);
    }
}
