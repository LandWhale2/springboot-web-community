package com.common.forum.service;

import lombok.AllArgsConstructor;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.common.forum.domain.entity.MemberEntity;
import com.common.forum.domain.repository.MemberRepository;
import com.common.forum.dto.MailDto;
import com.common.forum.dto.MemberDto;




@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private MemberRepository memberRepository;
    
    
    private static final String FROM_ADDRESS = "hostlandwhale@gmail.com";
    private static final String TITLE = "가입인증 메일 입니다.";

    public void mailSend(MailDto mailDto) {
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
    
    
    
    public MailDto setMailDto(MemberDto memberDto) {
    	String message = "http://localhost:8080/emailConfirm?authKey=" + memberDto.getAuthkey();
    	return MailDto.builder()
    			.address(memberDto.getEmail())
    			.title(TITLE)
    			.message(message)
    			.build();
    }
    
    @Transactional
    private MemberEntity getMemberEntityByAuthKey(String authkey) {
    	Optional<MemberEntity> memberEntityWrapper = memberRepository.findByAuthkey(authkey);
    	return memberEntityWrapper.get();
    }
    
    @Transactional
    public MemberDto convertMemberEntitytoDto(MemberEntity memberEntity) {
    	return MemberDto.builder()
    			.id(memberEntity.getId())
    			.email(memberEntity.getEmail())
    			.password(memberEntity.getPassword())
    			.nickname(memberEntity.getNickname())
    			.authkey(memberEntity.getAuthkey())
    			.isactive(memberEntity.isIsactive())
    			.build();
    }
    
    
    
    
    @Transactional
    public void authMember(String authkey) {
    	MemberEntity memberEntity = this.getMemberEntityByAuthKey(authkey);
    	MemberDto memberDto = this.convertMemberEntitytoDto(memberEntity);
    	
    	
    	memberDto.setIsactive(true);
    	
    	memberRepository.save(memberDto.toEntity());
    }
}
