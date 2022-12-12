package rts.ensea.fr;

import org.json.JSONObject;
import sun.security.mscapi.CPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;

public class EncryptedPayload {
    private UUID userUUID;
    private String operation;
    private String serializedCryptedPayload;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public EncryptedPayload(UUID userUUID, String operation, String serializedCryptedPayload, PublicKey publicKey,PrivateKey privateKey) {
        this.userUUID = userUUID;
        this.operation = operation;
        this.serializedCryptedPayload = serializedCryptedPayload;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
    public EncryptedPayload(User user, String operation, Payload payload, PublicKey publicKey,PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        this(
                UUID.nameUUIDFromBytes(user.serializeInJSON().toString().getBytes()),
                operation,
                null,
                publicKey,
                privateKey
        );
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(payload.serializeInJSON().toString().getBytes());
        this.serializedCryptedPayload = Base64.getEncoder().encodeToString(bytes);
    }

    public Payload getPayload() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnknownHostException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        String serializedPayload = new String(cipher.doFinal(Base64.getDecoder().decode(serializedCryptedPayload.getBytes())));
        return new Payload(new JSONObject(serializedPayload));
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public EncryptedPayload(JSONObject jsonObject, PublicKey publicKey, PrivateKey privateKey) throws UnknownHostException {
        this(
                UUID.fromString(jsonObject.getString("userUUID")),
                jsonObject.getString("operation"),
                jsonObject.getString("encryptedPayload"),
                publicKey,
                privateKey
        );
    }
    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("operation",operation);
        jsonobject.put("userUUID",userUUID.toString());
        jsonobject.put("encryptedPayload",serializedCryptedPayload);
        return jsonobject;
    }
}
