package com.muhardin.endy.belajar.enkripsi.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.muhardin.endy.belajar.enkripsi.dao.MemberDao;
import com.muhardin.endy.belajar.enkripsi.entity.Member;
import com.muhardin.endy.belajar.enkripsi.helper.CryptoHelper;

import lombok.extern.slf4j.Slf4j;

@Profile("aeslocal")
@Service @Slf4j
public class EncryptedLocalFileMemberService implements MemberInputService {

    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    @Autowired private CryptoHelper cryptoHelper;
    @Autowired private MemberDao memberDao;

    private MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

    @Override
    public void save(Member member, MultipartFile fileKtp) {
        try {
            member.setFileKtpMimeType(fileTypeMap.getContentType(fileKtp.getOriginalFilename()));
            member.setNoKtp(Base64.getEncoder().encodeToString(
                cryptoHelper.encrypt(member.getNoKtp().getBytes())));
            memberDao.save(member);

            Path destination = Paths.get(fileUploadFolder, member.getId());
            log.debug("Storing {} at {}", member.getId(), destination.toAbsolutePath());
            Files.createDirectories(Paths.get(fileUploadFolder));
            Files.write(destination, cryptoHelper.encrypt(fileKtp.getBytes()));
        } catch (IOException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException err) {
            log.error(err.getMessage(), err);
        }
    }

    @Override
    public byte[] getFileKtp(Member member) {
        try {
            String filename = fileUploadFolder + File.separator + member.getId();
            return cryptoHelper.decrypt(FileUtils.readFileToByteArray(new File(filename)));
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage(), e);
        }
        return new byte[0];
    }

    @Override
    public Iterable<Member> findAllMembers() {
        List<Member> memberList = new ArrayList<>();
        memberDao.findAll()
                .forEach(member -> {
                    try {
                        member.setNoKtpPlain(new String(
                            cryptoHelper.decrypt(
                                Base64.getDecoder().decode(member.getNoKtp()))));
                    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                            | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
                        log.debug(e.getMessage(),e);
                    }
                    memberList.add(member);
                });
        return memberList;
    }
    
}
