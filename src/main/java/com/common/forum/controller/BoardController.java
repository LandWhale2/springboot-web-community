package com.common.forum.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {
    @GetMapping("/test")
    public String test() {
        return "main.html";
    }

    @GetMapping("/signin")
    public String signin() {
        return "signIn.html";
    }
    @GetMapping("/signup")
    public String signup() {
        return "signUp.html";
    }
}

