package com.dongoh.project.springboot.web.dto.message;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MessageCheckRequestDto {
    private Long userId;

    @Builder
    MessageCheckRequestDto(Long userId)
    {
        this.userId=userId;
    }
}
