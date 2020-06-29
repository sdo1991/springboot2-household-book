package com.dongoh.project.springboot.web.dto.reply;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.reply.Reply;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
public class ReplyResponseDto {
    private Long id;
    private User author;
    private Post post;
    private String contents;
    private Boolean isMine;

    @Builder
    public ReplyResponseDto(Reply entity)
    {
        this.id=entity.getId();
        this.author=entity.getAuthor();
        this.post=entity.getPost();
        this.contents=entity.getContents().replace("\n", "\n<br>");
        this.isMine=false;
    }

    public void setIsMine()
    {
        this.isMine=true;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
}
