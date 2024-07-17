package com.muhardin.endy.belajar.enkripsi.helper;

import org.junit.jupiter.api.Test;

public class CryptoHelperTests {
    
    @Test 
    public void testGenerateKey() throws Exception {
        String key = CryptoHelper.generateKey();
        System.out.println("AES Key : "+key);
    }
}
