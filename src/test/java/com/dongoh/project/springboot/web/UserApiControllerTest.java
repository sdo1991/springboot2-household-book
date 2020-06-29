package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostRepository;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserRepository;
import com.dongoh.project.springboot.web.dto.post.PostWriteRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    //private MockMvc mvc;

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
        userRepository.deleteAll();
    }

    @Test
//    @WithMockUser(roles="USER")
    public void User_회원가입() throws Exception {
        //given
        String email="sdo1991@gmail.com";
        String name="신동오";
        String picture="picture.jpg";
        String introduce="안녕하세요";
        int score=0;
        Role role=Role.USER;

        UserJoinRequestDto requestDto = UserJoinRequestDto.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .introduce(introduce)
                .build();

        String url = "http://localhost:" + port + "/api/join";

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

        List<User> all = userRepository.findAll();
        User entity=all.get(0);

        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getEmail()).isEqualTo(email);
        assertThat(entity.getPicture()).isEqualTo(picture);
        assertThat(entity.getScore()).isEqualTo(score);
        assertThat(entity.getRole()).isEqualTo(role);
        assertThat(entity.getIntroduce()).isEqualTo(introduce);

    }

}
