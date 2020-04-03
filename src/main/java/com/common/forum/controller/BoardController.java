package com.common.forum.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
	@GetMapping("/")
    public String test() {
        return "main";
    }
}
