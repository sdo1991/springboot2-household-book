package com.dongoh.project.springboot.domain.post;

import com.dongoh.project.springboot.domain.BaseTimeEntity;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.post.PostResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author", nullable = false, updatable = false)
    private User author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column
    private String tags;

    @Column
    private Integer likes;

    @Column
    private Integer replies;


//    @Column(columnDefinition = "TEXT", nullable = true)
//    private String images;


    @Builder
    public Post(User author, String contents, String tags, Integer likes, Integer replies)
    {
        this.author=author;
        this.contents=contents;
        this.tags=tags;

        this.likes=likes;
        this.replies=replies;
    }


    public PostResponseDto toResponseDto()
    {
        return  PostResponseDto.builder().entity(this).build();
    }

}
