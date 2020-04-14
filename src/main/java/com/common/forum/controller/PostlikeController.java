package com.common.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.forum.dto.PostlikeDto;
import com.common.forum.service.PostlikeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/like")
public class PostlikeController {
	
	private PostlikeService postlikeService;
	
	
	
	@RequestMapping("/save")
	@ResponseBody
	private void postlike(@RequestParam Long bno) {
		postlikeService.likeSaveOrDelete(bno);
	}
	
	@RequestMapping("/count/{bno}")
	@ResponseBody
	private Long getcount(@PathVariable Long bno) {
		
		
		return postlikeService.getPostlikecount(bno);
	}
	
	

}
