package com.muhardin.endy.belajar.vault.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data")
public class DataController {

    @GetMapping("/form")
    public void inputData() {

    }

    @PostMapping("/form")
    public String processForm() {
        return "redirect:view";
    }

    @GetMapping("/view")
    public ModelMap viewData() {
        return new ModelMap();
    }
}
