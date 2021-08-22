package com.muhardin.endy.belajar.vault.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VaultServiceTests {
    @Autowired private VaultService vaultService;

    @Test
    public void testEncryptString() {
        String nik = "123456789";
        String encryptedNik = vaultService.encrypt(nik);
        Assertions.assertNotNull(encryptedNik);
        System.out.println("Encrypted : " + encryptedNik);
    }

    @Test
    public void testDecryptString() {
        String encryptedNik = "vault:v1:OZPO/nkobDFqMsCF5snQGgWoUtLdt2tp/lmr9zF/TAV4rRnH+A==";
        String decryptedNik = vaultService.decrypt(encryptedNik);
        Assertions.assertNotNull(decryptedNik);
        System.out.println("Decrypted : " + decryptedNik);
    }
}
