package com.dongoh.project.springboot.web.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ReplyLoadAllResponseDto {
    List<ReplyResponseDto> replies;
    Integer number;

    @Builder
    ReplyLoadAllResponseDto(List replies, Integer number)
    {
        this.replies=replies;
        this.number=number;
    }

}
