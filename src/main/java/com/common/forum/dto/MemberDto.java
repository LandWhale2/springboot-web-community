package com.common.forum.dto;


import lombok.*;
import java.time.LocalDateTime;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.common.forum.domain.entity.MemberEntity;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
	private Long id;
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;


	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
	
	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String nickname;
	
	
	@AssertTrue(message = "약관에 동의를 체크해주십시요.")
	private boolean terms;
	
	
	@AssertTrue(message = "이메일 중복 체크를 해주십시오.")
	private boolean emailcheck;
	
	private String authkey;
    private boolean isactive;
    
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .authkey(authkey)
                .isactive(isactive)
                .build();
    }
    
    
    @Builder
    public MemberDto(Long id, String email, String password, String nickname, String authkey, boolean isactive) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authkey = authkey;
        this.isactive = isactive;
    }
}
