package com.common.forum.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BoardController {
	@GetMapping("/test")
    public String test() {
        return "main";
    }
}
