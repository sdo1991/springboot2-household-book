package com.dongoh.project.springboot.web.dto.user;

import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Getter
@NoArgsConstructor
public class UserJoinRequestDto {

    private String email;
    private String name;
    private String picture;
    private String introduce;
    private Role role;

    private Integer score;
    private Date birthday;
    private String gender;
    private String mainLang;
    private String interestLang;
    private String password;
    private String userType;

    @Builder
    public UserJoinRequestDto(String email, String name, String picture, String introduce, Date birthday, String gender, String mainLang, String interestLang, String password, String userType, Role role)
    {
        this.email=email;
        this.name=name;
        this.picture=picture;
        this.introduce=introduce;
        this.birthday=birthday;
        this.gender=gender;
        this.mainLang=mainLang;
        this.interestLang=interestLang;
        this.password=password;
        this.userType=userType;

        this.score=0;

        this.role=role;
    }

    public User toEntity()
    {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .userType(userType)
                .score(score)
                .password(password)
                .mainLang(mainLang)
                .interestLang(interestLang)
                .gender(gender)
                .birthday(birthday)
                .introduce(introduce)


                .build();
    }

}
