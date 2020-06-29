package com.dongoh.project.springboot.web.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostLoadRequestDto {

    Integer page;
    Long userId;
    String url;

    @Builder
    PostLoadRequestDto(Integer page, Long userId, String url)
    {
        this.page=page;
        this.userId=userId;
        this.url=url;
    }

}
