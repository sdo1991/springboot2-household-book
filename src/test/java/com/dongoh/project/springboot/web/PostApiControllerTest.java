package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostRepository;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserRepository;
import com.dongoh.project.springboot.web.dto.post.PostWriteRequestDto;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

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


/*
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
*/
    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
//    @WithMockUser(roles="USER")
    public void Post_등록된다() throws Exception {
        //given
        String tags = "tags";
        String content = "content";

        User user=userRepository.save(User.builder()
                .email("sdo1991@gmail.com").name("동오").picture("pic.jpg").role(Role.USER).build());

        User author = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id:"+user.getId()));

        PostWriteRequestDto requestDto = PostWriteRequestDto.builder()
                .contents(content)
                .tags(tags)
                .author(author.getId())
                .build();

        String url = "http://localhost:" + port + "/api/post";


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

        List<Post> all = postsRepository.findAll();
        assertThat(all.get(0).getAuthor().getId()).isEqualTo(author.getId());
        assertThat(all.get(0).getContents()).isEqualTo(content);
        assertThat(all.get(0).getTags()).isEqualTo(tags);
    }

}
