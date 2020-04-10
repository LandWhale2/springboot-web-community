package com.common.forum.domain.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;
    
    @Column(length = 10, nullable = false)
    private String nickname;
    
    @Column(name = "ISACTIVE", nullable = false)
    private boolean isactive = false;
    
    @Column(length = 100, nullable = false)
    private String authkey;
    
    
    @OneToMany(mappedBy="memberEntity", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostEntity> post = new ArrayList<>();
    
    
    @Builder
    public MemberEntity(Long id, String email, String password, String nickname, boolean isactive, String authkey, List<PostEntity> post) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.isactive = isactive;
        this.authkey = authkey;
        this.post = post;
    }
}
