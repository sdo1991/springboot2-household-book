package com.dongoh.project.springboot.web.dto.post;

import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.image.ImagePostRequestDto;
import com.dongoh.project.springboot.web.dto.image.ImagePostResponseDto;
import com.dongoh.project.springboot.web.dto.reply.ReplyResponseDto;
import com.dongoh.project.springboot.web.dto.user.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostReadDto {
    private PostResponseDto post;
    private UserResponseDto author;
    private List<ReplyResponseDto> replies;
    private List<ImagePostResponseDto> images;
    private Integer remainReplies;

    @Builder
    public PostReadDto(PostResponseDto post, UserResponseDto author, List replies, List images, Integer remainReplies)
    {
        this.post=post;
        this.author=author;
        this.replies=replies;
        this.images=images;
        if(remainReplies>0)
            this.remainReplies=remainReplies;
    }

}
