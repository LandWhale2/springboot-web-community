package com.common.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class MemberController {
	
	
	
	@GetMapping("/signin")
	public String signin() {
		return "index/signIn";
	}
}
