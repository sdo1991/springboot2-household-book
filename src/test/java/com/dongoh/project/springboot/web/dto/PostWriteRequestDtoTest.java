package com.dongoh.project.springboot.web.dto;


import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserRepository;
import com.dongoh.project.springboot.service.PostService;
import com.dongoh.project.springboot.web.dto.post.PostWriteRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostWriteRequestDtoTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @After
    public void cleanUp()
    {

    }

    @Test
    public void postWriteRequestDto_테스트()
    {
        //given


        User user=userRepository.save(User.builder()
                .email("sdo1991@gmail.com").name("동오").picture("pic.jpg").role(Role.USER).build());

        User author = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id:"+user.getId()));

        PostWriteRequestDto postWriteRequestDto=PostWriteRequestDto.builder().author(author.getId()).contents("content").tags("tags###").build();
/*
        //when
        postService.save(postWriteRequestDto);



        //then
        assertThat(post.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(post.getContents()).isEqualTo("content");
        assertThat(post.getTags()).isEqualTo("tags###");
  */  }

}


