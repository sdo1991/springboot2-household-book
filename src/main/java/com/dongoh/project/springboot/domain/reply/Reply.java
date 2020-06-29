package com.dongoh.project.springboot.domain.reply;

import com.dongoh.project.springboot.domain.BaseTimeEntity;
import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.annotation.Target;

@Getter
@NoArgsConstructor
@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author", nullable = false, updatable = false)
    private User author;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post", nullable = false, updatable = false)
    private Post post;

    @Column(length = 1000, nullable = false)
    private String contents;



    @Builder
    public Reply(User author, Post post, String contents, Integer likes)
    {
        this.author=author;
        this.post=post;
        this.contents=contents;

    }

    public Reply update(String contents)
    {
        this.contents=contents;
        return this;
    }
}
