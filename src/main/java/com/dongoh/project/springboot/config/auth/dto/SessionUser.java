package com.dongoh.project.springboot.config.auth.dto;

import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.*;

@Getter
public class SessionUser extends org.springframework.security.core.userdetails.User implements Serializable {//User엔티티 클래스의 직렬화 DTO

    private Long id;
    private String email;
//    private String name;
//    private String picture;
//    private List hasRoles;

    public SessionUser(User user)
    {
        super(user.getEmail(), user.getPassword()
                , Arrays.asList(new SimpleGrantedAuthority(user.getRole().getKey())));

        this.id=user.getId();
        this.email=user.getEmail();
//        this.name=user.getName();
//        this.picture=user.getPicture();

//        this.hasRoles=Arrays.asList(new SimpleGrantedAuthority(user.getRole().getKey()));
    }

}
