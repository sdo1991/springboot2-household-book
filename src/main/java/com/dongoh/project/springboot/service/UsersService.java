package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserRepository;
import com.dongoh.project.springboot.web.dto.user.UserEmailValidCheckRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserRoleUpdateRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserUpdateRequestDto;
import com.dongoh.project.springboot.web.dto.util.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FindEntityService findEntityService;


    public ResultDto checkEmailValidate(String email, String userType)
    {
        ResultDto result=ResultDto.builder().Message(null).notError(null).build();

        if(userType!="normal")
            return result.setState(true, "구글 로그인");//특수문자있

        if(!email.matches("[0-9|a-z|A-Z|@|.]*")) {
            System.out.println("match error");
            return result.setState(false, "부적합한 문자가 포함되어 있습니다");//특수문자있
        }
        String[] tempArr=email.split("@");
        if(tempArr.length!=2) {
            System.out.println("@ error");
            return result.setState(false, "잘못된 형식의 email입니다");

        }
        /*
        if(email.split(".").length!=2) {
            System.out.println(". error");
            System.out.println(email);
            for(String s:email.split("."))
                System.out.println(s);

            return result.setState(false, "잘못된 형식의 email입니다");
        }*/
        String emailId=tempArr[0];

        String emailDomain=tempArr[1];
        if(!userType.equals("google")&&emailDomain.contains("google.com")) {
            System.out.println("google error");
            return result.setState(false, "google 유저는 google 로그인 해주세요");
        }

        if(isEmailDuplicate(email)==true) {
            System.out.println("success");

            return result.setState(true, "사용할 수 있는 email 입니다");
        }
        else {
            System.out.println("duplicate error");

            return result.setState(false, "중복된 email 입니다");
        }
    }

    @Transactional
    public boolean isEmailDuplicate(String email)
    {
        if(userRepository.countByEmail(email)==0)
            return true;
        else
            return false;
    }

    @Transactional
    public void updateRoleByUserId(UserRoleUpdateRequestDto requestDto)
    {
        User entity=findEntityService.findUserByUserId(requestDto.getUserId());
        Role role=Role.valueOf(requestDto.getRole());

        userRepository.save(entity.updateRole(role));
    }

    @Transactional
    public User join(UserJoinRequestDto requestDto) {


        User user=userRepository.findByEmail(requestDto.getEmail())
                .map(entity->entity.updateForJoin(requestDto))
                .orElse(requestDto.toEntity());

        if(user.getPassword()!=null)
            user=user.encryptPassword(passwordEncoder.encode(requestDto.getPassword()));

        return userRepository.save(user);
    }

    @Transactional
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저"));
    }

    @Transactional
    public User findByEmailAndRole(String email, Role role)
    {
        return userRepository.findByEmailAndRole(email, role);
    }

    @Transactional
    public Long update(UserUpdateRequestDto requestDto)
    {
        User userEntity=userRepository.findById(requestDto.getId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저"));
        userEntity=userEntity.updateUser(requestDto,passwordEncoder);

        return userRepository.save(userEntity).getId();
    }

    @Transactional
    public void delete() {

    }
/*
    @Transactional
    public User findUserByUserId(Long id)
    {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저. id:" + id));
    }
*/

}
