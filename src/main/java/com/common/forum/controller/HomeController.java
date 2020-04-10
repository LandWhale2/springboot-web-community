package com.common.forum.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.common.forum.dto.CategoryDto;
import com.common.forum.service.CategoryService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	
	private CategoryService categoryService;
	
	
	@GetMapping("/")
    public String home(Model model) {
		List<CategoryDto> categoryList = categoryService.getCategoryList();
		
		
		
		model.addAttribute("categoryList", categoryList);
        return "home";
    }
	
	
	

}
