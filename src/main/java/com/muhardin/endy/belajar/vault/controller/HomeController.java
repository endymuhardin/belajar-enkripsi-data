package com.muhardin.endy.belajar.vault.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Value("${file.upload.folder}")
    private String fileUploadFolder;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("fileUploadFolder", fileUploadFolder);
        return "home";
    }
}
