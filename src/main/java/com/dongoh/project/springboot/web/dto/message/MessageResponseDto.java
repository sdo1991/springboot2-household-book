package com.dongoh.project.springboot.web.dto.message;

import com.dongoh.project.springboot.domain.message.Message;
import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MessageResponseDto {
    private Long id;
    private User sender;
    private User receiver;
    private Post post;
    private String contents;
    private String url;
    private boolean isChecked;
    private String type;

    @Builder
    MessageResponseDto(Message entity)
    {
        this.id=entity.getId();
        this.sender=entity.getSender();
        this.receiver=entity.getReceiver();
        this.post=entity.getPost();
        this.url=entity.getUrl();
        this.isChecked=entity.isChecked();
        this.contents=entity.getContents();
        this.type=entity.getType();

    }
}
