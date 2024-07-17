package com.muhardin.endy.belajar.enkripsi.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.muhardin.endy.belajar.enkripsi.dao.MemberDao;
import com.muhardin.endy.belajar.enkripsi.entity.Member;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j @Profile("vault")
@Service @Transactional
public class EncryptedVaultMemberService implements MemberInputService {

    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    @Autowired private MemberDao memberDao;
    @Autowired private VaultService vaultService;

    @Override
    public void save(Member member, MultipartFile fileKtp) {

        try {
            member.setFileKtpMimeType(Files.probeContentType(new File(fileKtp.getOriginalFilename()).toPath()));
            member.setNoKtp(vaultService.encrypt(member.getNoKtp()));
            memberDao.save(member);
            String destinationFilename = fileUploadFolder + File.separator + member.getId();
            log.info("Upload file to {}", destinationFilename);
            FileUtils.writeStringToFile(new File(destinationFilename),
                    vaultService.encrypt(fileKtp.getBytes()),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public byte[] getFileKtp(Member member) {
        try {
            String filename = fileUploadFolder + File.separator + member.getId();
            return vaultService.decryptFile(
                    FileUtils.readFileToString(new File(filename), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public Iterable<Member> findAllMembers(){
        List<Member> memberList = new ArrayList<>();
        memberDao.findAll()
                .forEach(member -> {
                    member.setNoKtpPlain(vaultService.decrypt(member.getNoKtp()));
                    memberList.add(member);
                });
        return memberList;
    }
}
