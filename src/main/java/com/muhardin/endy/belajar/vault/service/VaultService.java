package com.muhardin.endy.belajar.vault.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultTransitKeyCreationRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service @Slf4j
public class VaultService {
    private static final String KEY_TYPE = "aes128-gcm96";
    private static final String KEY_ENCRYPT_KTP = "KEY_ENCRYPT_KTP";

    private VaultTransitOperations vaultTransit;

    public VaultService(VaultOperations vaultOperations) {
        vaultTransit = vaultOperations.opsForTransit();
        vaultTransit.createKey(KEY_ENCRYPT_KTP,
                VaultTransitKeyCreationRequest.ofKeyType(KEY_TYPE));
    }

    public String encrypt(String plaintext) {
        return vaultTransit.encrypt(KEY_ENCRYPT_KTP, plaintext);
    }

    public String decrypt(String cipherText) {
        return vaultTransit.decrypt(KEY_ENCRYPT_KTP, cipherText);
    }

    public File encrypt(File plainFile){
        try {
            String base64Encrypted = encrypt(FileUtils.readFileToByteArray(plainFile));
            File result = File.createTempFile(plainFile.getName()+"%", "-enc.txt");
            FileUtils.writeStringToFile(
                    result,
                    base64Encrypted, StandardCharsets.UTF_8);
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public File decrypt(File cipherFile) {
        try {
            File result = File.createTempFile(UUID.randomUUID().toString(), ".png");
            FileUtils.writeByteArrayToFile(result, decryptFile(FileUtils.readFileToString(cipherFile, StandardCharsets.UTF_8)));
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String encrypt(byte[] fileContent) {
        return encrypt(Base64.getEncoder().encodeToString(fileContent));
    }

    public byte[] decryptFile(String encryptedFileContent) {
        return Base64.getDecoder().decode(decrypt(encryptedFileContent));
    }
}
