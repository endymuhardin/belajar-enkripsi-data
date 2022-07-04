package com.muhardin.endy.belajar.vault.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import com.muhardin.endy.belajar.enkripsi.service.VaultService;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class VaultServiceTests {
    @Value("classpath:sample-file/ktp-plain.png")
    private Resource fileKtpPlain;

    @Value("classpath:sample-file/ktp-encrypted.txt")
    private Resource fileKtpEncrypted;

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

    @Test
    public void testEncryptFile() throws Exception {
        File encryptedKtp = vaultService.encrypt(fileKtpPlain.getFile());
        System.out.println("Encrypted file : " + encryptedKtp.getAbsolutePath());
    }

    @Test
    public void testDecryptFile() throws IOException {
        File decryptedKtp = vaultService.decrypt(fileKtpEncrypted.getFile());
        System.out.println("Decrypted file : "+ decryptedKtp.getAbsolutePath());
    }
}
