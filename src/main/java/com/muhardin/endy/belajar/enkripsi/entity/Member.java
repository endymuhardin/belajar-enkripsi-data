package com.muhardin.endy.belajar.enkripsi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data @Entity
public class Member {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @NotNull @NotEmpty
    private String nama;

    @NotNull @NotEmpty
    private String noKtp;

    @Transient
    private String noKtpPlain;

    private String fileKtpMimeType;
}
