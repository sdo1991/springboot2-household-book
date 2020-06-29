package com.dongoh.project.springboot.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserEmailValidCheckRequestDto {
    String email;

    @Builder
    UserEmailValidCheckRequestDto(String email)
    {
        this.email=email;
    }


}
