package com.dongoh.project.springboot.domain.post;

import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private User user;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post", nullable = false, updatable = false)
    private Post post;

    @Builder
    public PostLike(User user, Post post)
    {
        this.user=user;
        this.post=post;
    }
}
