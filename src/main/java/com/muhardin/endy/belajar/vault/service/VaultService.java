package com.muhardin.endy.belajar.vault.service;

import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.VaultTransitKeyCreationRequest;

@Service
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
}
