package com.dongoh.project.springboot.domain.image;

import com.dongoh.project.springboot.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post", nullable = false, updatable = false)
    private Post post;

    @Column
    private String imageName;

    @Column
    private Integer number;

    @Column(nullable = true)
    private String state;

    @Builder
    public PostImage(Post post, String imageName, Integer number, String state)
    {
        this.post=post;
        this.imageName=imageName;
        this.number=number;
        this.state=state;
    }

}
