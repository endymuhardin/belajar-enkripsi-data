package com.muhardin.endy.belajar.enkripsi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data @Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull @NotEmpty
    private String nama;

    @NotNull @NotEmpty
    private String noKtp;

    @Transient
    private String noKtpPlain;

    private String fileKtpMimeType;
}
