package com.dongoh.project.springboot.domain.post;

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
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanUp(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void post_만들기()
    {
        //given
        User author;
        String contents="<img src=\"sdfsd\">sdfolsf</img>";
        String tags="#태그1#태그2#태그3";

        author=userRepository.save(User.builder()
                .email("sdo1991@gmail.com").name("동오").picture("pic.jpg").role(Role.USER).build());

        postRepository.save(Post.builder().author(author).contents(contents).tags(tags).build());

        //when
        List<Post> postList=postRepository.findAll();

        //then

        Post post=postList.get(0);

        assertThat(post.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(post.getContents()).isEqualTo(contents);
        assertThat(post.getTags()).isEqualTo(tags);
    }
}
