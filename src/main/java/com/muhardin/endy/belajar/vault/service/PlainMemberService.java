package com.muhardin.endy.belajar.vault.service;

import com.muhardin.endy.belajar.vault.dao.MemberDao;
import com.muhardin.endy.belajar.vault.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

@Slf4j
@Service @Profile("default")
public class PlainMemberService implements MemberInputService {

    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    @Autowired private MemberDao memberDao;

    private MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();;

    @Override
    public void save(Member member, MultipartFile fileKtp) {
        try {
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
}
