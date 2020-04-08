package com.common.forum.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.forum.dto.CommentDto;
import com.common.forum.service.CommentService;
import com.common.forum.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value ="/comment", method={RequestMethod.POST,RequestMethod.GET})
@AllArgsConstructor
public class CommentController {
	
	private CommentService commentService;
	
	
	
	@RequestMapping("/list/{bno}")
	@ResponseBody
	private List<CommentDto> commentList(@PathVariable Long bno) throws Exception {
		return commentService.getCommentList(bno);
	}
	
	
	@RequestMapping(value="/insert", method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	private Long commentsave(@RequestParam Long bno, @RequestParam String content) throws Exception {
		
		String writer = MemberService.currentUserNickname();
		return commentService.saveComment(bno, writer, content);
	}
	
	
	@RequestMapping("/update")
	@ResponseBody
	private Long commentupdate(@RequestParam Long cno, @RequestParam String content) throws Exception {
		return commentService.updateComment(cno, content);
	}
	
	
	@RequestMapping("/delete/{cno}")
	@ResponseBody
	private void commentdelete(@PathVariable Long cno) throws Exception {
		commentService.deleteComment(cno);
	}
	

}
