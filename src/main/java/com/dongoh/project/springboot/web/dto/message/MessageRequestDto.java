package com.dongoh.project.springboot.web.dto.message;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequestDto {

    private User sender;
    private User receiver;
    private Post post;
    private String contents;
    private String url;
    private boolean isChecked;
    private String type;

    @Builder
    MessageRequestDto(User sender, User receiver, Post post, String contents, String url, boolean isChecked, String type)
    {
        this.sender=sender;
        this.receiver=receiver;
        this.post=post;
        this.url=url;
        this.isChecked=isChecked;
        this.contents=contents;
        this.type=type;

    }
}
