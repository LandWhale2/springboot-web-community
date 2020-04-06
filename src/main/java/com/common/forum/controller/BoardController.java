package com.common.forum.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
	@GetMapping("/")
    public String test() {
        return "main";
    }
	
//	@GetMapping("/category")
//	public String asd() {
//		return "category";
//	}
//	
//	@PostMapping("/category")
//	public String catego() {
//		System.out.println("asd");;
//		return "redirect:/";
//	}
}
