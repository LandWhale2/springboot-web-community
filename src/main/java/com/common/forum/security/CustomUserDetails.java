package com.common.forum.security;



import java.util.Collection;



import org.springframework.security.core.SpringSecurityCoreVersion;

import org.springframework.security.core.userdetails.User;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CustomUserDetails extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // 유저의 정보를 더 추가하고 싶다면 이곳과, 아래의 생성자 파라미터를 조절해야 한다.
    private String user_email;
    private String user_name;

    public CustomUserDetails(String username, String password
            , boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked
            , Collection authorities
            , String user_email, String user_name) {
        super(username, password
                , enabled, accountNonExpired, credentialsNonExpired, accountNonLocked
                , authorities);
        this.user_email = user_email;
        this.user_name = user_name;
    }
}
