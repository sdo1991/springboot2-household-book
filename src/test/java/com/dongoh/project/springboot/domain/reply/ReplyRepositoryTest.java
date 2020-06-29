package com.dongoh.project.springboot.domain.reply;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostRepository;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanUp(){
        replyRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void reply_쓰기()
    {
        //given
        User post_author=userRepository.save(User.builder().email("sdo1991@gmail.com").name("동오").picture("pic.jpg").role(Role.USER).build());
        User reply_author=userRepository.save(User.builder().email("sdo1991@gddd.com").name("신동오").picture("pic2.jpg").role(Role.USER).build());
        Post post=postRepository.save(Post.builder().author(post_author).contents("<p>콘텐츠</p>").tags("#tag").build());

        replyRepository.save(Reply.builder().author(reply_author).post(post).contents("content").build());

        List<Reply> replyList=replyRepository.findAll();


        //when
        Reply reply=replyList.get(0);

        //then
        assertThat(reply.getAuthor().getId()).isEqualTo(reply_author.getId());
        assertThat(reply.getPost().getId()).isEqualTo(post.getId());

        assertThat(reply.getContents()).isEqualTo("content");
    }

}
