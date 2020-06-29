package com.dongoh.project.springboot.web.dto.reply;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
public class ReplyWriteRequestDto {

    private Long author;
    private Long post;
    private String contents;

    @Builder
    ReplyWriteRequestDto(Long author, Long post, String contents){
        this.author=author;
        this.post=post;
        this.contents=contents;
    }

}
