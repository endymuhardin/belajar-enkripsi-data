package com.muhardin.endy.belajar.vault.helper;

import org.junit.jupiter.api.Test;

import com.muhardin.endy.belajar.enkripsi.helper.CryptoHelper;

public class CryptoHelperTests {
    
    @Test 
    public void testGenerateKey() throws Exception {
        String key = CryptoHelper.generateKey();
        System.out.println("AES Key : "+key);
    }
}
