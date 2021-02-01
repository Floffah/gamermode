package dev.floffah.gamermode.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class VarInt {
    // modified from https://wiki.vg/Protocol#VarInt_and_VarLong
    public static int readVarInt(DataInputStream in) throws IOException {
            int numRead = 0;
            int result = 0;
            byte read;
            do {
                read = in.readByte();
                int value = (read & 0b01111111);
                result |= (value << (7 * numRead));

                numRead++;
                if (numRead > 5) {
                    throw new RuntimeException("VarInt is too big");
                }
            } while ((read & 0b10000000) != 0);

            return result;
    }
    // modified from https://wiki.vg/Protocol#VarInt_and_VarLong
    public static int readVarInt(ByteArrayDataInput in) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = in.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    // modified from https://wiki.vg/Protocol#VarInt_and_VarLong
    public static void writeVarInt(DataOutputStream out, int value) throws IOException {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            out.writeByte(temp);
        } while (value != 0);
    }
    // modified from https://wiki.vg/Protocol#VarInt_and_VarLong
    public static void writeVarInt(ByteArrayDataOutput out, int value) throws IOException {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            out.writeByte(temp);
        } while (value != 0);
    }
}
