package com.dongoh.project.springboot.web.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReplyDeleteRequestDto {

    Long id;

    @Builder
    public ReplyDeleteRequestDto(Long id)
    {
        this.id=id;
    }
}
