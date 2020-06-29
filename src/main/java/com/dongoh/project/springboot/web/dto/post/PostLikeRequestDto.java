package com.dongoh.project.springboot.web.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostLikeRequestDto {
    private Long userId;
    private Long postId;

    @Builder
    PostLikeRequestDto(Long userId, Long postId)
    {
        this.userId=userId;
        this.postId=postId;
    }
}
