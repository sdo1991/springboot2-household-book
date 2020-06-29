package com.dongoh.project.springboot.web.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostDeleteRequestDto {
    private Long postId;

    @Builder
    PostDeleteRequestDto(Long postId)
    {
        this.postId=postId;
    }
}
