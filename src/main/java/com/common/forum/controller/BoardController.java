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
    public String test(Model model) {
		List<CategoryDto> categoryList = categoryService.getCategoryList();
		
		model.addAttribute("categoryList", categoryList);
        return "main";
    }
	
	
	
	@GetMapping("/{category}")
	public String listByCategory(Model model, @RequestParam(value="page", defaultValue= "1") Integer pageNum,@PathVariable("category") String category) {
		
		
		
		List<PostDto> postList = postService.getPostListByCategoryid(category, pageNum);
		Integer[] pageList = postService.getPageList(pageNum, category);
		List<CategoryDto> categoryList = categoryService.getCategoryList();
		
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("category", category);
		model.addAttribute("boardList", postList);
		model.addAttribute("pageList", pageList);
		
		return "";
	}

	
	@GetMapping("/{category}/writing")
    public String write(Model model, @PathVariable("category") String category) {
    	model.addAttribute("category", category);
        return "writing";
    }
	
	
	//  게시물 쓰기
    
	@PostMapping("/{category}/writing")
	public String write(PostDto postDto) {
  	
		//로그인중인 닉네임
		String writer = MemberService.currentUserNickname();
		postDto.setWriter(writer);
  	
  	
		postService.savePost(postDto);
      

		return "redirect:/";
	}
	
	//게시물 디테일
	@GetMapping("/{category}/detail/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
    	PostDto postDTO = postService.getPostDto(no);
    	postService.addhit(no);
    	
    	model.addAttribute("boardDto", postDTO);
    	return "board/detail.html";
    }
	
	
	//게시물 수정
	@GetMapping("/{category}/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
    	PostDto postDTO = postService.getPostDto(no);
    	
    	model.addAttribute("boardDto", postDTO);
    	return "board/update.html";
    }
	
	@PostMapping("/{category}/edit/{no}")
    public String update(PostDto boardDTO) {
        postService.updatePost(boardDTO);

        return "redirect:/";
    }
	
	
	//게시물 삭제
	@PostMapping("/{category}/detail/{no}")
    public String delete(@PathVariable("no") Long no) {
        postService.deletePost(no);

        return "redirect:/";
    }
	
	
	//게시물 검색
	@GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
    	List<PostDto> postDtoList = postService.searchPosts(keyword);
    	
    	model.addAttribute("boardList", postDtoList);
    	
    	return "board/list.html";
    }
	
	
}
