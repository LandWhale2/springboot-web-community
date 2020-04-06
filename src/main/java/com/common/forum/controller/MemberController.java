package com.common.forum.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
		System.out.println("asdad");
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
	
	
	

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "member/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "member/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "main";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo(HttpServletRequest httpRequest) {
        return "main";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "main";
    }
    
    
    @GetMapping({"/loginSuccess", "/hello"})
    public String loginSuccess() {
    	return "main";
    }
}
