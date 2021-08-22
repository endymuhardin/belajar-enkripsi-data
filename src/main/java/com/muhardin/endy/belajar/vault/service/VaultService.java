package com.muhardin.endy.belajar.vault.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultTransitKeyCreationRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.UUID;

@Service @Slf4j
public class VaultService {
    private static final String KEY_TYPE = "aes128-gcm96";
    private static final String KEY_ENCRYPT_STRING = "KEY_ENCRYPT_STRING";

    private VaultTransitOperations vaultTransit;

    public VaultService(VaultOperations vaultOperations) {
        vaultTransit = vaultOperations.opsForTransit();
        vaultTransit.createKey(KEY_ENCRYPT_STRING,
                VaultTransitKeyCreationRequest.ofKeyType(KEY_TYPE));
    }

    public String encrypt(String plaintext) {
        return vaultTransit.encrypt(KEY_ENCRYPT_STRING, plaintext);
    }

    public String decrypt(String cipherText) {
        return vaultTransit.decrypt(KEY_ENCRYPT_STRING, cipherText);
    }

    public File encrypt(File plainFile){
        try {
            String base64Encoded = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(plainFile));
            String base64Encrypted = encrypt(base64Encoded);
            File result = File.createTempFile(plainFile.getName()+"%", "-enc.txt");
            FileUtils.writeStringToFile(
                    result,
                    base64Encrypted, Charset.defaultCharset());
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public File decrypt(File cipherFile) {
        try {
            String cipherFileContent = FileUtils.readFileToString(cipherFile, Charset.defaultCharset());
            String base64Encoded = decrypt(cipherFileContent);
            File result = File.createTempFile(UUID.randomUUID().toString(), ".png");
            byte[] decryptedFileContent = Base64.getDecoder().decode(base64Encoded);
            FileUtils.writeByteArrayToFile(result, decryptedFileContent);
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
