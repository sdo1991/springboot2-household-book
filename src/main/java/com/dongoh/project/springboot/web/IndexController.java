package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostLike;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.service.*;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeRequestDto;
import com.dongoh.project.springboot.web.dto.post.PostWriteRequestDto;
import com.dongoh.project.springboot.web.dto.reply.ReplyWriteRequestDto;
import com.dongoh.project.springboot.web.dto.user.FollowRequestDto;
import com.dongoh.project.springboot.web.dto.user.FollowerListResponseDto;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;
    private final PostService postsService;
    private final UsersService usersService;
    private final ReplyService replyService;
    private final FindEntityService findEntityService;
    private final MessageService messageService;
    private final SimpleNoticeService simpleNoticeService;
    private final UserFollowService userFollowService;

    boolean isInit=false;

    User adminUser;
    User[] sampleUser;

    @GetMapping("/test-init")
    public String testInit()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse("1991-07-04");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!isInit) {//테스트 유저
            isInit=true;

            UserJoinRequestDto userJoinRequestDto
                    =UserJoinRequestDto.builder()
                    .name("신동오")
                    .email("sdo91@naver.com")
                    .introduce("안녕하세요")
                    .picture("/image/test-image.jpg")
                    .gender("male")
                    .mainLang("ko")
                    .interestLang("ja,en")
                    .password("qwer")
                    .role(Role.ADMIN)
                    .birthday(date)
                    .userType("normal")
                    .build();
            Long userId = usersService.join(userJoinRequestDto).getId();
            adminUser = findEntityService.findUserByUserId(userId);



            sampleUser=new User[30];
            String[] names= {"김민준","이준","한주원","김도현","이지민","안예은","신민지","한가은","이하율","신도림",
                    "佐藤 悠真", "鈴木 悠人", "高橋 陽翔","田中 蓮","伊藤 湊","山本 大翔","中村 蓮","小林 陽翔","加藤 新","田中 結菜",
                    "Oliver","James","Jack","Harry","Robert","Jacob","Michael","John","Jack","Trump"};

            String[] emails={"sdo13@naver.com","zze@jam.clo","sf@Fsdvcsk.com","jfojewof@cjso.com","IAMHERO@sdfiei.com",
                    "dffff@codi.com","csfjo@foosc.cjds","adsd@fd.ccczz","sfasf@fdsio.ff","qwer2@sdfdc.com",
                    "sdo13@na1ver.com","z1ze@jam.clo","sf@Fsd1vcsk.com","jfojewo1f@cjso.com","IAMHERO@1sdfiei.com",
                    "dfff2f@codi.com","csfjo2@foosc.cjds","ads2@fd.ccczz","sfa2sf@fdsio.ff","qw2er2@sdfdc.com",
                    "sd3o13@naver.com","zze3@jam.clo","sf@3Fsdvcsk.com","jfojew3of@cjso.com","IAM3HERO@sdfiei.com",
                    "dffff@co4di.com","csfjo@4foosc.cjds","adsd@fd.cc4czz","sfasf@4fdsio.ff","q4wer2@sdfdc.com",
                    };
            String[] introduces= {"안녕하세요", "안녕하시오", "저는 누구입니다", "인사인사", "아가나다", "", "띠요오오옹", "", "", "ㅇㅇㅇ",
                    "こんにちは", "おはよう", "おはようございます。", "初めまして。", "ちくしょう", "疲れた", "ははは", "ひひ", "ののの", "あああああ",
                    "introduce", "hello", "My name is Mike", "ill", "boring", "no more", "aaaa", "this is test", "iamhero", "iamlegend"
                    };

            for(int i=0;i<sampleUser.length;i++)
            {
                String gender="";
                if(i%2==0)
                    gender="male";
                else
                    gender="female";
                String interestLang="";
                String language="";
                if(i<10) {
                    language = "ko";
                    if(i%3==0)
                        interestLang="ja,en";
                    else if(i%3==1)
                        interestLang="ja";
                    else
                        interestLang="en";
                }
                else if(i<20) {
                    language = "ja";

                    if(i%3==0)
                        interestLang="ko,en";
                    else if(i%3==1)
                        interestLang="ko";
                    else
                        interestLang="en";
                }
                else {
                    language = "en";

                    if(i%3==0)
                        interestLang="ko,ja";
                    else if(i%3==1)
                        interestLang="ko";
                    else
                        interestLang="ja";
                }

                userJoinRequestDto
                        =UserJoinRequestDto.builder()
                        .name(names[i])
                        .email(emails[i])
                        .introduce(introduces[i])
                        .picture("/image/sample-picture/profile ("+(i+1)+").jpg")
                        .gender(gender)
                        .mainLang(language)
                        .interestLang(interestLang)
                        .password("qwer")
                        .role(Role.USER)
                        .birthday(date)
                        .userType("normal")
                        .build();
                userId = usersService.join(userJoinRequestDto).getId();
                sampleUser[i] = findEntityService.findUserByUserId(userId);
            }

            Post postEntity=null;
            PostWriteRequestDto requestDto;

            for(int i=0;i<=1000;i++)
            {
                Random random=new Random();
                int rand=random.nextInt(sampleUser.length);

                String tag="";
                if(rand%2==0)
                    tag+="#잡담";
                if(rand%3==0)
                    tag+="#질문";
                if(rand%5==0)
                    tag+="#취미";
                if(rand%6==0)
                    tag+="#고쳐주세요";
                if(tag.equals(""))
                    tag="#잡담";

                String contents="";
                if(rand<10)
                {
                    contents="저는 "+sampleUser[rand].getName()+"입니다.";
                }
                else if(rand<20)
                {
                    contents="私は "+sampleUser[rand].getName()+"です。";
                }
                else
                {
                    contents="I'm "+sampleUser[rand].getName()+".";

                }


                requestDto=PostWriteRequestDto.builder().tags(tag).contents(contents).author(sampleUser[rand].getId()).build();

                long postId=postsService.writePost(requestDto);
//                postEntity=findEntityService.findPostById(postId);
            }
            requestDto=PostWriteRequestDto.builder().tags("#잡담").contents("안녕하세요.\n헬로월드에 오신 것을 환영합니다.").author(adminUser.getId()).build();
            postEntity=findEntityService.findPostById(postsService.writePost(requestDto));

            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[0].getId()).contents("안녕하세요").post(postEntity.getId()).build());

            for(int i=0;i<sampleUser.length;i++)
            {
                PostLike postLikeEntity=PostLike.builder()
                        .post(postEntity)
                        .user(sampleUser[i])
                        .build();
                findEntityService.sendMessageForPostLike(postLikeEntity);
            }

            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[1].getId()).contents("반가워요").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[12].getId()).contents("初めまして").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[27].getId()).contents("Hello").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[2].getId()).contents("수고하셨습니다").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[14].getId()).contents("疲れた").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[14].getId()).contents("本当に\n助けて").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[25].getId()).contents("Cool").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[22].getId()).contents("I wanna sleep").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[18].getId()).contents("お疲れ様です。").post(postEntity.getId()).build());
            replyService.writeReply(ReplyWriteRequestDto.builder().author(sampleUser[3].getId()).contents("여기까지").post(postEntity.getId()).build());

            /*
            for(int i=0;i<sampleUser.length;i++)
                userFollowService.addFollow(FollowRequestDto.builder().receiveUserId(sampleUser[i].getId()).sendUserId(adminUser.getId()).build());
*/


            simpleNoticeService
                    .saveDefaultNotice(SimpleNoticeRequestDto.builder()
                            .contents("이 페이지는 샘플 페이지 입니다.").title("공지").isActivated(true).type("success").language("ko").groupNum(1L)
                            .build());
            simpleNoticeService
                    .saveDefaultNotice(SimpleNoticeRequestDto.builder()
                            .contents("このページはサンプルページです。").title("お知らせ").isActivated(true).type("success").language("ja").groupNum(1L)
                            .build());
            simpleNoticeService
                    .saveDefaultNotice(SimpleNoticeRequestDto.builder()
                            .contents("This is a sample page").title("Notice").isActivated(true).type("success").language("en").groupNum(1L)
                            .build());
        }


        return "redirect:main";
    }

}
