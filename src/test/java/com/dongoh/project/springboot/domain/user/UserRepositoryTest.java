package com.dongoh.project.springboot.domain.user;

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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanUp()
    {
        userRepository.deleteAll();
    }

    @Test
    public void User_등록()
    {
        //given
        String email="sdoq1991@gmail.com";
        String name="신동오";
        int score=0;
        String picture="picture.jpg";
        String introduce="안녕하세요";
        Role role=Role.USER;

        userRepository.save(User.builder().email(email).name(name).picture(picture).role(role).introduce(introduce).build());

        //when
        List<User> userList=userRepository.findAll();

        //then
        User user=userList.get(0);

        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPicture()).isEqualTo(picture);
        assertThat(user.getScore()).isEqualTo(0);
        assertThat(user.getRole()).isEqualTo(role);
        assertThat(user.getIntroduce()).isEqualTo(introduce);

    }

}
