package com.common.forum.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.common.forum.domain.entity.MemberEntity;
import com.common.forum.domain.repository.MemberRepository;
import com.common.forum.dto.MailDto;
import com.common.forum.dto.MemberDto;
import com.common.forum.security.CustomUserDetails;
import com.common.forum.security.Role;
import com.common.forum.security.TempKey;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

	private MailService mailService;
	
    private MemberRepository memberRepository;
    
    @Transactional
    public void joinUser(MemberDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        
        memberDto.setAuthkey(new TempKey().getKey(50, false));
        
        
        memberRepository.save(memberDto.toEntity());
        
        MailDto mailDto = mailService.setMailDto(memberDto);
        mailService.mailSend(mailDto);
    }

	
	
	@Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<MemberEntity> userEntityWrapper = memberRepository.findByEmail(userEmail);
        MemberEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        

        if (("khj879123@gmail.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else if(userEntity.isIsactive()) {
        	authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }else {
        	authorities.add(new SimpleGrantedAuthority(Role.GUEST.getValue()));
        }
        
        
        
        CustomUserDetails user = new CustomUserDetails(
        		userEntity.getEmail(),
        		userEntity.getPassword(),
        		true, true, true, true,
        		authorities,
        		userEntity.getEmail(),
        		userEntity.getNickname());
        
        
        System.out.println(user);
        
        
        return user;
    }
	
	
	public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }
	
	
	
	public static String currentUserNickname() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    	return user.getUser_name();
    }
	
	
}
