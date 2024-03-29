package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.VarInt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Outgoing login packet for requesting encryption
 */
public class EncryptionRequest extends BasePacket {
    /**
     * Construct an EncryptionRequest
     */
    public EncryptionRequest() {
        super("LoginEncryptionRequest", 0x01, PacketType.OUTBOUND);
    }

    @Override
    public ByteArrayDataOutput buildOutput() throws IOException {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        VarInt.writeVarInt(out, 0);
        try {
            // generate & write a keypair
            conn.kp = conn.main.server.kpg.generateKeyPair();
            byte[] e = conn.kp.getPublic().getEncoded();
            VarInt.writeVarInt(out, e.length);
            for (byte b : e) {
                out.writeByte(b);
            }

            // generate & write a verification token
            conn.verifytoken = new byte[5];
            SecureRandom.getInstanceStrong().nextBytes(conn.verifytoken);

            VarInt.writeVarInt(out, conn.verifytoken.length);
            for (byte b : conn.verifytoken) {
                out.writeByte(b);
            }
        } catch (NoSuchAlgorithmException e) {
            conn.main.server.logger.printStackTrace(e);
        }

        return out;
    }
}
