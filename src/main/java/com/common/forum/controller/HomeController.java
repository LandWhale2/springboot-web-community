package com.common.forum.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.common.forum.dto.CategoryDto;
import com.common.forum.dto.PostDto;
import com.common.forum.service.CategoryService;
import com.common.forum.service.HomeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	
	private CategoryService categoryService;
	private HomeService homeService;
	
	@GetMapping("/")
    public String home(Model model) {
		List<CategoryDto> categoryList = categoryService.getCategoryList();
		
		for (CategoryDto categoryDto : categoryList) {
			List<PostDto> postDto = homeService.getHomeBoard(categoryDto.getName());
			String categoryPostName = categoryDto.getName();
			
			model.addAttribute(categoryPostName, postDto);
		}
		
		
		model.addAttribute("categoryList", categoryList);
        return "home";
    }
	
	
	

}
