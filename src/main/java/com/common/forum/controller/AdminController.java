package com.common.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminController {
	
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	
	
	@PostMapping("/admin/category")
	public String category() {
		return "redirect:/admin";
	}
	
}
