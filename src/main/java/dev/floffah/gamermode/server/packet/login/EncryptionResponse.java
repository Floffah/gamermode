package dev.floffah.gamermode.server.packet.login;

import com.google.common.io.ByteArrayDataInput;
import dev.floffah.gamermode.server.packet.BasePacket;
import dev.floffah.gamermode.server.packet.PacketType;
import dev.floffah.gamermode.util.VarInt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Incoming login packet for processing the client's encryption response
 */
public class EncryptionResponse extends BasePacket {
    /**
     * Construct an EncryptionResponse
     */
    public EncryptionResponse() {
        super("LoginEncryptionResponse", 0x01, PacketType.INBOUND);
    }

    @Override
    public void process(int len, ByteArrayDataInput in) throws IOException {
        // temporary cipher
        Cipher tempc;

        // read the shared secret
        int sslen = VarInt.readVarInt(in);
        byte[] ss = new byte[sslen];
        for (int i = 0; i < sslen; i++) {
            ss[i] = in.readByte();
        }

        // received verify token
        int vtlen = VarInt.readVarInt(in);
        byte[] vt = new byte[vtlen];
        for (int i = 0; i < vtlen; i++) {
            vt[i] = in.readByte();
        }

        // create temporary cipher
        try {
            tempc = Cipher.getInstance("RSA");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            conn.main.server.logger.printStackTrace(e);
            conn.disconnect("&cError while enabling key decryption");
            return;
        }

        // decrypt secret
        try {
            tempc.init(Cipher.DECRYPT_MODE, conn.kp.getPrivate());
            conn.secret = new SecretKeySpec(tempc.doFinal(ss), "AES");
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            conn.main.server.logger.printStackTrace(e);
            conn.disconnect("&cError while decrypting clients secret key");
            return;
        }

        // decrypt verification token
        byte[] clientverify;
        try {
            tempc.init(Cipher.DECRYPT_MODE, conn.kp.getPrivate());
            clientverify = tempc.doFinal(vt);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            conn.main.server.logger.printStackTrace(e);
            conn.disconnect("&cError while decrypting verify token");
            return;
        }

        // make sure its the same as the server sent
        if (!Arrays.equals(conn.verifytoken, clientverify)) {
            conn.disconnect("&cInvalid verify token");
            return;
        }

        // initialise the cipher used for the rest of the communication
        try {
            conn.eciph = Cipher.getInstance("AES/CFB8/NoPadding");
            conn.eciph.init(Cipher.ENCRYPT_MODE, conn.secret, new IvParameterSpec(conn.secret.getEncoded()));
            conn.dciph = Cipher.getInstance("AES/CFB8/NoPadding");
            conn.dciph.init(Cipher.DECRYPT_MODE, conn.secret, new IvParameterSpec(conn.secret.getEncoded()));
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            conn.main.server.logger.printStackTrace(e);
            conn.disconnect("&cError while enabling stream encryption");
            return;
        }

        // hash the secret, random session code, and public key
        try {
            conn.digest = MessageDigest.getInstance("SHA-1");
            conn.digest.update(conn.session.getBytes());
            conn.digest.update(conn.secret.getEncoded());
            conn.digest.update(conn.kp.getPublic().getEncoded());
            conn.hash = new BigInteger(conn.digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            conn.main.server.logger.printStackTrace(e);
            conn.disconnect("&cError while creating hash");
            return;
        }

        // encode the clients address
        InetAddress addr = conn.main.sock.getInetAddress();
        conn.prox = URLEncoder.encode(addr.getHostAddress(), StandardCharsets.UTF_8);

        // enable encryption
        conn.encrypted = true;
        conn.fin.enableDecryption(conn.dciph);
        conn.fout.enableEncryption(conn.eciph);

        // initialise keepalive
        conn.lastkeepalivereceive = System.currentTimeMillis();

        // send next packets
        conn.send(new LoginSuccess());
        conn.send(new JoinGame());
    }
}
