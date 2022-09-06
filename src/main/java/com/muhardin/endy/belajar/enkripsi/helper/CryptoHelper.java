package com.muhardin.endy.belajar.enkripsi.helper;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CryptoHelper {
    private static final String ALGORITHM_KEY = "AES";
    private static final String ALGORITHM_ENCRYPTION = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int AES_KEY_LENGTH = 256;

    @Value("${aes.encryption.key}")
    private String aesKeyString;
    private SecretKey secretKey;

    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM_KEY);
        keygen.init(AES_KEY_LENGTH);
        SecretKey key = keygen.generateKey();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    
    @PostConstruct
    public void initialize(){
        secretKey = new SecretKeySpec(Base64.getDecoder().decode(aesKeyString), ALGORITHM_KEY);
    }
    
    public byte[] encrypt(byte[] plainContent) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        byte[] iv = generateIV();
        
        Cipher cipher = Cipher.getInstance(ALGORITHM_ENCRYPTION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] cipherText = cipher.doFinal(plainContent);
        return ByteBuffer.allocate(iv.length + cipherText.length)
                .put(iv)
                .put(cipherText)
                .array();
    }

    public byte[] decrypt(byte[] encryptedContent) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        ByteBuffer bb = ByteBuffer.wrap(encryptedContent);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM_ENCRYPTION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);
        return cipher.doFinal(cipherText);
    }

    private byte[] generateIV(){
        byte[] nonce = new byte[IV_LENGTH_BYTE];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }
}
