package com.dongoh.project.springboot.web.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReplyMoreRepliesLoadReqeustDto {
    Long postId;
    Long userId;

    @Builder
    ReplyMoreRepliesLoadReqeustDto(Long postId, Long userId)
    {
        this.postId=postId;
        this.userId=userId;
    }
}
