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

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j @Profile("default")
@Service @Transactional
public class PlainMemberService implements MemberInputService {

    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    @Autowired private MemberDao memberDao;

    private MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();;

    @Override
    public void save(Member member, MultipartFile fileKtp) {
        try {
            Files.createDirectories(Paths.get(fileUploadFolder));
            member.setFileKtpMimeType(fileTypeMap.getContentType(fileKtp.getOriginalFilename()));
            memberDao.save(member);
            String destinationFilename = fileUploadFolder + File.separator + member.getId();
            log.info("Upload file to {}", destinationFilename);
            fileKtp.transferTo(new File(destinationFilename));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public byte[] getFileKtp(Member member) {
        try {
            String filename = fileUploadFolder + File.separator + member.getId();
            return FileUtils.readFileToByteArray(new File(filename));
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
                    member.setNoKtpPlain(member.getNoKtp());
                    memberList.add(member);
                });
        return memberList;
    }
}
