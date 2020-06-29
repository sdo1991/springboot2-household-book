package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostRepository;
import com.dongoh.project.springboot.domain.reply.Reply;
import com.dongoh.project.springboot.domain.reply.ReplyRepository;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserRepository;
import com.dongoh.project.springboot.web.dto.post.PostWriteRequestDto;
import com.dongoh.project.springboot.web.dto.reply.ReplyWriteRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReplyApiControllerTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    //private MockMvc mvc;

    @Autowired
    private PostRepository postsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @After
    public void tearDown() throws Exception {
        replyRepository.deleteAll();
        postsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
//    @WithMockUser(roles="USER")
    public void Reply_등록된다() throws Exception {
        //given
        User author;
        Post post;
        String contents="reply test";


        User user1=userRepository.save(User.builder()
                .email("sdo1991@gmail.com").name("동오").picture("pic.jpg").role(Role.USER).build());
        User user2=userRepository.save(User.builder()
                .email("sdo1991@gl.com").name("오").picture("c.jpg").role(Role.USER).build());

        author=user1;
        post=postsRepository.save(Post.builder()
                .tags("tags").contents("contents")
                .author(user2).build());

        ReplyWriteRequestDto requestDto = ReplyWriteRequestDto.builder()
                .author(author.getId())
                .contents(contents)
                .post(post.getId())
                .build();

        String url = "http://localhost:" + port + "/api/reply";


        //when
        /*
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
*/
        ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Reply> all = replyRepository.findAll();
        Reply entity=all.get(0);

        assertThat(entity.getPost().getId()).isEqualTo(post.getId());
        assertThat(entity.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(entity.getContents()).isEqualTo(contents);
    }



}
