package com.common.forum.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import com.common.forum.dto.MemberDto;
import com.common.forum.service.MailService;
import com.common.forum.service.MemberService;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;
    private MailService mailService;


    @GetMapping("/user/login")
    public String signin() {
        return "signIn";
    }


    @GetMapping("/user/signup")
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

            return "signup";
        }
        model.addAttribute("memberDto", memberDto);
        memberService.joinUser(memberDto);


        return "redirect:/user/signup/result";
    }

    @RequestMapping(value = "/emailConfirm", method = RequestMethod.GET)
    public String emailConfirm(@RequestParam("authKey") String authkey,
                               Model model, RedirectAttributes rttr) throws Exception {

        if (authkey == null) {
            rttr.addFlashAttribute("msg", "인증키가 잘못되었습니다. 다시 인증해 주세요");
            return "redirect:/";
        }

        mailService.authMember(authkey);


        return "authOk";
    }


    @GetMapping("/user/signup/result")
    public String signResult() {
        return "authorizing";
    }


    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "member/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "redirect:/";
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


    @GetMapping({"/loginSuccess", "/hello"})
    public String loginSuccess() {
        return "main";
    }


    @RequestMapping("/checkemail/{email}")
    @ResponseBody
    public String checkemail(@PathVariable String email) throws Exception {
        return memberService.checkEmail(email);
    }
}
