package com.common.forum.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.forum.dto.CategoryDto;
import com.common.forum.dto.PostDto;
import com.common.forum.service.CategoryService;
import com.common.forum.service.MemberService;
import com.common.forum.service.PostService;

import lombok.AllArgsConstructor;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
	private PostService postService;
	private CategoryService categoryService;
	
	
	@GetMapping("/")
    public String test() {
		
        return "main";
    }
	
	
	
	@GetMapping("/{category}")
	public String listByCategory(Model model, @RequestParam(value="page", defaultValue= "1") Integer pageNum,@PathVariable("category") String category) {
		
		
		
		List<PostDto> postList = postService.getPostListByCategoryid(category, pageNum);
		Integer[] pageList = postService.getPageList(pageNum, category);
		CategoryDto categoryDto = categoryService.getcategoryDto(category);
		
		
		
		model.addAttribute("category", category);
		model.addAttribute("boardList", postList);
		model.addAttribute("pageList", pageList);
		
		return "";
	}

	
	@GetMapping("/{category}/writing")
    public String write(Model model, @PathVariable("category") String category) {
    	model.addAttribute("categoryList", category);
        return "writing";
    }
	
}
