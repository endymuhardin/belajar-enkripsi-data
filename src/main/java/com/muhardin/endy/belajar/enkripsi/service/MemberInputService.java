package com.muhardin.endy.belajar.enkripsi.service;

import org.springframework.web.multipart.MultipartFile;

import com.muhardin.endy.belajar.enkripsi.entity.Member;

public interface MemberInputService {
    void save(Member member, MultipartFile fileKtp);
    byte[] getFileKtp(Member member);
    Iterable<Member> findAllMembers();
}
