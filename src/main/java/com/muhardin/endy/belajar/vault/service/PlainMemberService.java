package com.muhardin.endy.belajar.vault.service;

import com.muhardin.endy.belajar.vault.dao.MemberDao;
import com.muhardin.endy.belajar.vault.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service @Profile("default")
public class PlainMemberService implements MemberInputService {

    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    @Autowired private MemberDao memberDao;

    @Override
    public void save(Member member, MultipartFile fileKtp) {
        try {
            memberDao.save(member);
            String destinationFilename = fileUploadFolder + File.separator +
                    member.getId() + "." + FilenameUtils.getExtension(fileKtp.getOriginalFilename());
            log.info("Upload file to {}", destinationFilename);
            fileKtp.transferTo(new File(destinationFilename));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
