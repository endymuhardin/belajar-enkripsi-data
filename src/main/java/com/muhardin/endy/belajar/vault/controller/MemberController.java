package com.muhardin.endy.belajar.vault.controller;

import com.muhardin.endy.belajar.vault.entity.Member;
import com.muhardin.endy.belajar.vault.service.MemberInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired private MemberInputService memberInputService;

    @GetMapping("/form")
    public ModelMap inputData() {
        return new ModelMap()
                .addAttribute("inputFormDto", new Member());
    }

    @PostMapping("/form")
    public String processForm(@ModelAttribute @Valid Member member,
                              BindingResult errors, SessionStatus status,
                              @RequestPart("fileKtp") MultipartFile fileKtp) {

        if (errors.hasErrors()) {
            return "member/form";
        }

        memberInputService.save(member, fileKtp);

        status.setComplete();
        return "redirect:list";
    }

    @GetMapping("/list")
    public ModelMap viewData() {
        return new ModelMap()
                .addAttribute(memberInputService.findAllMembers());
    }

    @GetMapping("/{id}/ktp")
    public ResponseEntity<byte[]> getFotoKtp(@PathVariable("id") Member member) {
        byte[] data = memberInputService.getFileKtp(member);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(member.getFileKtpMimeType()))
                .body(data);
    }

}
