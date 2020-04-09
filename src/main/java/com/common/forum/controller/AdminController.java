package com.common.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.common.forum.dto.CategoryDto;
import com.common.forum.service.CategoryService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminController {
	
	private CategoryService categoryService;
	
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	
	
	@PostMapping("/admin/category")
	public String category(CategoryDto categoryDto) {
		categoryService.saveCategory(categoryDto);
		return "redirect:/admin";
	}
	
}
