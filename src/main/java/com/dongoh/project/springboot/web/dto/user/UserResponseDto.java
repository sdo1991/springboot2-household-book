package com.dongoh.project.springboot.web.dto.user;

import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String picture;
    private Role role;

    private Integer score;
    private Date birthday;
    private String gender;
    private String mainLang;
    private String[] interestLang;
    private String interestLangText;
    private String introduce;
    private String userType;


    @Builder
    public UserResponseDto(User user)
    {
        this.introduce=user.getIntroduce();
        this.id=user.getId();
        this.email=user.getEmail();
        this.name=user.getName();
        this.score=user.getScore();
        this.role=user.getRole();
        this.picture=user.getPicture();
        this.birthday=user.getBirthday();
        this.gender=user.getGender();
        this.mainLang=user.getMainLang();
        if(user.getInterestLang()!=null)
            this.interestLang=user.getInterestLang().split(",");

        this.interestLangText=user.getInterestLang();

        this.userType=user.getUserType();
    }


}
