package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.web.dto.post.PostReadDto;
import com.dongoh.project.springboot.web.dto.post.PostWriteRequestDto;
import com.dongoh.project.springboot.web.dto.reply.ReplyWriteRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    UsersService usersService;
    @Autowired
    PostService postService;
    @Autowired
    ReplyService replyService;

    @After
    public void cleanUp()
    {

    }



    @Test
    public void test()
    {

        SimpleGrantedAuthority sga=new SimpleGrantedAuthority("GUEST");
    }

    @Test
    public void findAllDesc_테스트()
    {/*
        Long postUserId= usersService.join(UserJoinRequestDto.builder().name("user1").email("sdo19@dic.com").picture("pic.pc").introduce("hellooo").build());
        Long replyUserId1= usersService.join(UserJoinRequestDto.builder().name("user2").email("sdo1dd9@ddic.com").picture("cpic.pc").introduce("hellosoo").build());
        Long postId= postService.writePost(PostWriteRequestDto.builder().author(postUserId).contents("post contetn").tags("tags").build());
        Long replyId1=replyService.writeReply(ReplyWriteRequestDto.builder().post(postId).contents("reply1").author(replyUserId1).build());

        List<PostReadDto> dtos=postService.findAllDesc();

        assertThat(dtos.get(0).getPost().getId()).isEqualTo(postId);
        assertThat(dtos.get(0).getAuthor().getId()).isEqualTo(postUserId);
        assertThat(dtos.get(0).getReplies().get(0).getId()).isEqualTo(replyId1);
    */}
}
