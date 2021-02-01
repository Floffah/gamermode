package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.server.packet.connection.LoginDisconnect;
import dev.floffah.gamermode.util.VarInt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptionResponse extends BasePacket {
    public EncryptionResponse() {
        super("LoginEncryptionResponse", 0x01, PacketType.INBOUND);
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        int sslen = VarInt.readVarInt(in);
        byte[] ss = new byte[sslen];
        for (int i = 0; i < sslen; i++) {
            ss[i] = in.readByte();
        }
        conn.ssecret = ss;

        int vtlen = VarInt.readVarInt(in);
        byte[] vt = new byte[vtlen];
        for (int i = 0; i < vtlen; i++) {
            vt[i] = in.readByte();
        }

        byte[] decrypted;
        try {
            Cipher c = Cipher.getInstance(conn.kp.getPublic().getAlgorithm());
            c.init(Cipher.DECRYPT_MODE, conn.kp.getPrivate());
            decrypted = c.doFinal(vt);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            conn.main.server.logger.printStackTrace(e);
            return;
        }
        if (!Arrays.equals(conn.verifytoken, decrypted)) {
            conn.send(new LoginDisconnect("""
                    {
                      "text": "Invalid verify token",
                      "color": "red"
                    }"""));
            conn.close();
        } else {
            conn.send(new LoginSuccess());
        }
    }
}
