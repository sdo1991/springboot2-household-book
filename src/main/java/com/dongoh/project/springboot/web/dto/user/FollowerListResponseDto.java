package com.dongoh.project.springboot.web.dto.user;

import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FollowerListResponseDto {
    Long userId;
    String picture;
    String name;

    @Builder
    FollowerListResponseDto(User userEntity)
    {
        userId=userEntity.getId();
        picture=userEntity.getPicture();
        name=userEntity.getName();
    }

}
