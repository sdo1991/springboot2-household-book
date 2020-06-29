package com.dongoh.project.springboot.web.dto.reply;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
public class ReplyUpdateRequestDto {
    private Long id;
    //private User author;
    //private Post post;
    private String contents;

    @Builder
    ReplyUpdateRequestDto(Long id, String contents)
    {
        this.id=id;
        this.contents=contents;
    }

}
