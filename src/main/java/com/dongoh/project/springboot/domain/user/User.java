package com.dongoh.project.springboot.domain.user;

import com.dongoh.project.springboot.domain.BaseTimeEntity;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserResponseDto;
import com.dongoh.project.springboot.web.dto.user.UserUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "User", uniqueConstraints = {@UniqueConstraint(
        name = "User_Unique", columnNames = {"email"})})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer score;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 500)
    private String introduce;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date birthday;

    @Column
    private String gender;

    @Column
    private String mainLang;

    @Column
    private String interestLang;

    @Column
    private String password;

    @Column
    private String userType;


    @Builder
    public User(Long id, String email, String name, String picture, Role role, String introduce, Date birthday, String gender, String mainLang, String interestLang, String password, Integer score, String userType)
    {
        this.id=id;
        this.email=email;
        this.name=name;
        this.picture=picture;
        this.role=role;
        this.introduce=introduce;

        this.birthday=birthday;
        this.gender=gender;
        this.mainLang=mainLang;
        this.interestLang=interestLang;


        this.password=password;
        this.userType=userType;
        this.score=score;
    }


    public User updateUser(UserUpdateRequestDto requestDto, BCryptPasswordEncoder passwordEncoder)
    {
        if(requestDto.getBirthday()!=null)
            this.birthday=requestDto.getBirthday();
        if(requestDto.getGender()!=null)
            this.gender=requestDto.getGender();
        if(requestDto.getInterestLang()!=null)
            this.interestLang=requestDto.getInterestLang();
        if(requestDto.getIntroduce()!=null)
            this.introduce=requestDto.getIntroduce();
        if(requestDto.getMainLang()!=null)
            this.mainLang=requestDto.getMainLang();
        if(requestDto.getName()!=null)
            this.name=requestDto.getName();
        if(requestDto.getPicture()!=null)
            this.picture=requestDto.getPicture();

        if(!requestDto.getPassword().equals(""))
            this.password=passwordEncoder.encode(requestDto.getPassword());

        return this;
    }

    public User updateBySocial(String name, String picture)
    {
        this.name=name;
        this.picture=picture;

        return this;
    }


    public User updateForJoin(UserJoinRequestDto requestDto)
    {
        this.name=requestDto.getName();
        this.userType=requestDto.getUserType();
        this.password=requestDto.getPassword();
        this.interestLang=requestDto.getInterestLang();
        this.mainLang=requestDto.getMainLang();
        this.gender=requestDto.getGender();
        this.birthday=requestDto.getBirthday();
        this.introduce=requestDto.getIntroduce();
        this.picture=requestDto.getPicture();
        this.role=requestDto.getRole();
        this.email=requestDto.getEmail();
        this.score=requestDto.getScore();

        return this;
    }

    public User encryptPassword(String encryptedPassword)
    {
        password=encryptedPassword;
        return this;
    }
    public User updateRole(Role role)
    {
        this.role=role;
        return this;
    }

    public String getRoleKey()
    {
        return this.role.getKey();
    }

    public UserResponseDto toResponseDto()
    {
        return UserResponseDto.builder().user(this).build();
    }

}
