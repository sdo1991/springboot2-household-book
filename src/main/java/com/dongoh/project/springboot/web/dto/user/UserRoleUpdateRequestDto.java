package com.dongoh.project.springboot.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRoleUpdateRequestDto {
    private Long userId;
    private String role;

    @Builder
    UserRoleUpdateRequestDto(Long userId, String role)
    {
        this.userId=userId;
        this.role=role;
    }
}
