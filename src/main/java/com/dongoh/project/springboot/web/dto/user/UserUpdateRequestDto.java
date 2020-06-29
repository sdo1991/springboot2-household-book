package com.dongoh.project.springboot.web.dto.user;

import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDto {

    private Long id;
    private String name;
    private String picture;
    private String introduce;
    private Date birthday;
    private String gender;
    private String mainLang;
    private String interestLang;
    private String password;

    @Builder
    public UserUpdateRequestDto(Long id, String name, String picture, String introduce, Date birthday, String gender, String mainLang, String interestLang, String password)
    {
        this.id=id;
        this.name=name;
        this.picture=picture;
        this.introduce=introduce;
        this.birthday=birthday;
        this.gender=gender;
        this.mainLang=mainLang;
        this.interestLang=interestLang;
        this.password=password;
    }

    public User toEntity()
    {
        return User.builder().id(id).name(name).picture(picture).interestLang(interestLang).introduce(introduce)
                .birthday(birthday).gender(gender).mainLang(mainLang).password(password).build();
    }
}
