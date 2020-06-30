package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.config.auth.dto.SessionUser;
import com.dongoh.project.springboot.domain.image.PostImage;
import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.reply.Reply;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.service.*;
import com.dongoh.project.springboot.web.dto.image.ImagePostRequestDto;
import com.dongoh.project.springboot.web.dto.message.MessageCheckRequestDto;
import com.dongoh.project.springboot.web.dto.post.PostLikeRequestDto;
import com.dongoh.project.springboot.web.dto.post.PostWriteRequestDto;
import com.dongoh.project.springboot.web.dto.user.FollowRequestDto;
import com.dongoh.project.springboot.web.dto.user.FollowerListResponseDto;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final PostService postsService;
    private final HttpSession httpSession;

    private final UsersService usersService;
    private final MessageService messageService;
    private final ReplyService replyService;
    private final FindEntityService findEntityService;
    private final ImageService imageService;
    private final SimpleNoticeService simpleNoticeService;
    private final UserFollowService userFollowService;

    @GetMapping("/main")
    public String main(Model model, HttpServletRequest request, Authentication authentication)
    {//모델에 MainPosts(Post, User, Reply)를 넣음.

        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);
        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        model.addAttribute("user", userResponseDto);
        model.addAttribute("posts", postsService.findPosts(user, 0,request.getParameter("post_id"),false));
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        model.addAttribute("notice", simpleNoticeService.findNoticeByLanguage(user.getMainLang()));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        return "main";
    }

    @GetMapping("/reject_page")
    public String rejectPage(Model model, HttpServletRequest request, Authentication authentication)
    {//모델에 MainPosts(Post, User, Reply)를 넣음.
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);
        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        model.addAttribute("user", userResponseDto);

        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        if(user.getRole().getKey().equals(Role.USER.getKey()))
            model.addAttribute("isUser", true);
        else
            model.addAttribute("isUser", false);

        if(user.getRole().getKey().equals(Role.BLOCK.getKey()))
            model.addAttribute("isBlock", true);
        else
            model.addAttribute("isBlock", false);

        return "reject_page";
    }



    @GetMapping("/admin_post_page")
    public String adminPostPage(Model model, HttpServletRequest request, Authentication authentication)
    {//모델에 MainPosts(Post, User, Reply)를 넣음.

        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);
        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        model.addAttribute("user", userResponseDto);
        model.addAttribute("posts", postsService.findPosts(user, 0,request.getParameter("post_id"),true));
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        model.addAttribute("notice", simpleNoticeService.findNoticeByLanguage(user.getMainLang()));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        return "main";
    }



    @GetMapping("/follower_page")
    public String followerPage(Model model, HttpServletRequest request, Authentication authentication)
    {
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);

        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        List<FollowerListResponseDto> followersDto= userFollowService.findFollowerByUser(user);
        Long[] followers=new Long[followersDto.size()];
        for(int i=0;i<followers.length;i++)
            followers[i]=followersDto.get(i).getUserId();

        model.addAttribute("follower", followersDto);
        model.addAttribute("user", userResponseDto);

        model.addAttribute("posts", postsService.findPostsForFollowers(user,0,followers));

        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        model.addAttribute("notice", simpleNoticeService.findNoticeByLanguage(user.getMainLang()));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        return "follower_page";
    }

    @GetMapping("/admin_report_page")
    public String adminReportPage(Model model, HttpServletRequest request, Authentication authentication)
    {
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);

        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        String page=request.getParameter("page");
        if(page==null)
            page="1";

        model.addAttribute("user", userResponseDto);
        model.addAttribute("message", messageService.findAllReports(user, Integer.parseInt(page)));
        model.addAttribute("lastPage", (Integer)(messageService.countMessageByType("report")/10));
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);


        return "admin_report_page";
    }

    @GetMapping("/admin_notice_page")
    public String adminNoticePage(Model model, HttpServletRequest request, Authentication authentication)
    {
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);

        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        String page=request.getParameter("page");
        if(page==null)
            page="1";

        model.addAttribute("user", userResponseDto);
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        model.addAttribute("notice", simpleNoticeService.findAllNotice());
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);


        return "admin_notice_page";
    }


    @GetMapping("/my_message")
    public String my_message(Model model, HttpServletRequest request, Authentication authentication)
    {
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);

        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        String page=request.getParameter("page");
        if(page==null)
            page="1";

        model.addAttribute("user", userResponseDto);
        model.addAttribute("message", messageService.findMessageByUserId(user, Integer.parseInt(page)));
        model.addAttribute("lastPage", (Integer)(messageService.countMessageByUserId(user)/10));
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        //model.addAttribute("notice", simpleNoticeService.findNoticeByLanguage(user.getMainLang()));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);


        return "my_message";
    }


    @GetMapping("/tag_page")
    public String tagPage(Model model, HttpServletRequest request, Authentication authentication)
    {
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);

        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        model.addAttribute("user", userResponseDto);
        model.addAttribute("posts", postsService.findPostsByTag(user,0,request.getParameter("tag")));
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        model.addAttribute("notice", simpleNoticeService.findNoticeByLanguage(user.getMainLang()));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        return "tag_page";
    }

    @GetMapping("/user_page")
    public String userPage(Model model, HttpServletRequest request, Authentication authentication)
    {
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);

        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        Long targetUserId=Long.parseLong(request.getParameter("user_id"));

        model.addAttribute("user", userResponseDto);
        model.addAttribute("target_user", UserResponseDto.builder().user(findEntityService.findUserByUserId(targetUserId)).build());
        model.addAttribute("posts", postsService.findUserInfoPost(user,0,request.getParameter("user_id")));
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        model.addAttribute("notice", simpleNoticeService.findNoticeByLanguage(user.getMainLang()));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        boolean isFollower=userFollowService.checkFollow(FollowRequestDto.builder().sendUserId(user.getId()).receiveUserId(targetUserId).build());
        model.addAttribute("isFollower",isFollower);


        return "user_page";
    }


    @GetMapping("/update_user")
    public String updateUser(Model model, HttpServletRequest request, Authentication authentication)
    {
        User user=null;
        String userEmail="";

        if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class))//소셜 로그인
        {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();
            userEmail=(String) defaultOAuth2User.getAttributes().get("email");
        }
        else//폼 로그인
        {
            userEmail=(String) authentication.getPrincipal();
        }

        user=usersService.findByEmail(userEmail);

        UserResponseDto userResponseDto=UserResponseDto.builder().user(user).build();

        model.addAttribute("user", userResponseDto);
        model.addAttribute("alarm",messageService.findAlarmMessageByUserId(user));
        if(user.getRole().getKey().equals(Role.ADMIN.getKey()))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);


        return "update_user";
    }

    @GetMapping("/")
    public String index()
    {
        return "redirect:main";
    }

    @GetMapping("/login_page")
    public String login(Model model, HttpServletRequest request)
    {
        return "login_page";
    }


    @GetMapping("/form_login_success")
    public String formLoginSuccess(Authentication authentication)
    {
//        SessionUser sessionUser=(SessionUser) user;
//        httpSession.setAttribute("user",new SessionUser(user));
        //System.out.println(sessionUser.getName());
        //System.out.println(sessionUser.getEmail());
        System.out.println(authentication);

        //httpSession.setAttribute("user", sessionUser);

        System.out.println("do login");
        return "redirect:main";

    }

    @GetMapping("/oauth2_login_success")
    public String oauth2LoginSuccess(RedirectAttributes redirectAttributes)
    {
        DefaultOAuth2User principal = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("google login info");
        System.out.println(principal.getAttributes());
        System.out.println(principal.getAuthorities());

        Map<String, Object> attributes=principal.getAttributes();


        User entity=usersService.findByEmailAndRole((String)attributes.get("email"),Role.USER);


        if(entity!=null)
            return "redirect:main";
        else {

            redirectAttributes.addAttribute("email",  attributes.get("email"));
            redirectAttributes.addAttribute("name", attributes.get("name"));
            redirectAttributes.addAttribute("picture", attributes.get("picture"));
            //redirectAttributes.addAttribute("locale",attributes.get("locale"));


            return "redirect:register_google_page";
        }
    }

    @GetMapping("/register_normal_page")
    public String registerNormal(Model model, HttpServletRequest request)
    {
        return "register_normal_page";
    }


    @GetMapping("/register_google_page")
    public String registerGoogle(Model model, HttpServletRequest request,
                           @RequestParam(value="email") String email,
                           @RequestParam(value="name") String name,
                           @RequestParam(value="picture") String picture)
                           //@RequestParam(value="locale") String locale)
    {
        System.out.println("google Login");
        User userEntity=User.builder().email(email).name(name).picture(picture).userType("google").build();
        UserResponseDto userDto=UserResponseDto.builder().user(userEntity).build();
        model.addAttribute("user",userDto);

        return "register_google_page";
    }



}


/*
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts",postsService.findAllDesc());

        if(user!=null)
            model.addAttribute("userName",user.getName());

        return "index";
    }

 */