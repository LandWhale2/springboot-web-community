package com.common.forum.controller;

import java.util.Map;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.common.forum.dto.MemberDto;
import com.common.forum.service.MemberService;

import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class MemberController {
	
	private MemberService memberService;
	
	
	
	@GetMapping("/signin")
	public String signin() {
		return "signIn";
	}
	
	
	@GetMapping("/signup")
	public String signup(MemberDto memberDto) {
		return "signUp.html";
	}
	
	
	
	
	@PostMapping("/user/signup")
    public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model) {
    	if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("memberDto", memberDto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "member/signup";
        }
    	model.addAttribute("memberDto", memberDto);
        memberService.joinUser(memberDto);
        


        return "redirect:/";
    }
}
