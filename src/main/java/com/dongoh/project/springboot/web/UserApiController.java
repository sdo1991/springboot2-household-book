package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.config.auth.dto.SessionUser;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.service.FindEntityService;
import com.dongoh.project.springboot.service.ImageService;
import com.dongoh.project.springboot.service.UsersService;
import com.dongoh.project.springboot.web.dto.image.ImagePostRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserEmailValidCheckRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserRoleUpdateRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserUpdateRequestDto;
import com.dongoh.project.springboot.web.dto.util.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UsersService usersService;
    private final ImageService imageService;
    private final HttpSession httpSession;
    private final FindEntityService findEntityService;

    public void login(){}

    @PostMapping("/api/email_check")
    public ResultDto emailValidCheck(@RequestBody UserEmailValidCheckRequestDto requestDto)
    {

        return usersService.checkEmailValidate(requestDto.getEmail(),"normal");

//        return "message";
    }

    @PutMapping("/api/role_update")
    public void roleUpdate(@RequestBody UserRoleUpdateRequestDto requestDto)
    {
        System.out.println(requestDto.getRole());
        System.out.println(requestDto.getUserId());
        usersService.updateRoleByUserId(requestDto);

    }

    @PutMapping("/api/update_user")
    public Long updateUser(MultipartHttpServletRequest mtfRequest) throws ParseException {
        String path=mtfRequest.getSession().getServletContext().getRealPath("/");
        MultipartFile mf=mtfRequest.getFile("profile_image");
        String picture=mtfRequest.getParameter("default_image");

        if (mf.getSize()>0) {
            picture=imageService.uploadProfileImage(mf, path);
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date birthday=sdf.parse(mtfRequest.getParameter("birthday"));
        String interestLang=mtfRequest.getParameter("interest_lang").replace(" ","");

        UserUpdateRequestDto requestDto=
                UserUpdateRequestDto.builder()
                        .picture(picture)
                        .birthday(birthday)
                        .introduce(mtfRequest.getParameter("introduce"))
                        .name(mtfRequest.getParameter("name"))
                        .gender(mtfRequest.getParameter("gender"))
                        .interestLang(interestLang)
                        .mainLang(mtfRequest.getParameter("main_lang"))
                        .password(mtfRequest.getParameter("password"))
                        .id(Long.parseLong(mtfRequest.getParameter("user_id")))
                        .build();

        return usersService.update(requestDto);

    }

    @PostMapping("/api/join")
    public ResultDto join(MultipartHttpServletRequest mtfRequest) throws ParseException {
        String path=mtfRequest.getSession().getServletContext().getRealPath("/");
        MultipartFile mf=mtfRequest.getFile("profile_image");
        String picture=mtfRequest.getParameter("default_image");

        String email=mtfRequest.getParameter("email");
        String userType=mtfRequest.getParameter("user_type");

        ResultDto resultDto=usersService.checkEmailValidate(email, userType);
        if(resultDto.getNotError()==false)
            return resultDto;

        resultDto.addParam("userType",userType);

        if (mf.getSize()>0) {
            picture=imageService.uploadProfileImage(mf, path);
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date birthday=sdf.parse(mtfRequest.getParameter("birthday"));
        String interestLang=mtfRequest.getParameter("interest_lang").replace(" ","");

        UserJoinRequestDto requestDto=
                UserJoinRequestDto.builder()
                .picture(picture)
                .birthday(birthday)
                .introduce(mtfRequest.getParameter("introduce"))
                .email(email)
                .name(mtfRequest.getParameter("name"))
                .gender(mtfRequest.getParameter("gender"))
                .interestLang(interestLang)
                .mainLang(mtfRequest.getParameter("main_lang"))
                .userType(userType)
                .password(mtfRequest.getParameter("password"))
                .role(Role.USER)
                .build();

        User user=usersService.join(requestDto);
//        SessionUser user=(SessionUser) httpSession.getAttribute("user");

//        httpSession.setAttribute("user",new SessionUser(user));

        resultDto.setMessage("환영합니다."+user.getName()+"님");
        
        return resultDto;

    }

    public void update(){}

    public void withdrwal(){}

    public void getUser(){}

}
