package com.muhardin.endy.belajar.enkripsi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.config.EnvironmentVaultConfiguration;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTemplate;

@Configuration
@PropertySource("classpath:application.properties")
@Import(EnvironmentVaultConfiguration.class)
public class VaultConfig  {

    @Bean
    public VaultOperations vaultOperations(EnvironmentVaultConfiguration config) {
        return new VaultTemplate(config.vaultEndpoint(), config.clientAuthentication());
    }
}
