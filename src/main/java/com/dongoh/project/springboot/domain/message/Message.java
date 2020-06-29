package com.dongoh.project.springboot.domain.message;


import com.dongoh.project.springboot.domain.BaseTimeEntity;
import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.message.MessageRequestDto;
import com.dongoh.project.springboot.web.dto.message.MessageResponseDto;
import com.dongoh.project.springboot.web.dto.post.PostResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "sender", nullable = false, updatable = false)
    private User sender;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "receiver", nullable = false, updatable = false)
    private User receiver;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post", updatable = false)
    private Post post;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column
    private String url;

    @Column
    private boolean isChecked;

    @Column
    private String type;


    @Builder
    public Message(MessageRequestDto requestDto)
    {
        this.sender=requestDto.getSender();
        this.receiver=requestDto.getReceiver();
        this.post=requestDto.getPost();
        this.contents=requestDto.getContents();
        this.url=requestDto.getUrl();
        this.isChecked=requestDto.isChecked();
        this.type=requestDto.getType();
    }
    public MessageResponseDto toResponseDto()
    {
        return MessageResponseDto.builder().entity(this).build();
    }

}
