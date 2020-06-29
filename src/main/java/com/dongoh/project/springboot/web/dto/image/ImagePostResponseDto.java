package com.dongoh.project.springboot.web.dto.image;

import com.dongoh.project.springboot.domain.image.PostImage;
import com.dongoh.project.springboot.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
public class ImagePostResponseDto {
    private Long id;
    private Post post;
    private String imageName;
    private Integer number;
    private String state;

    @Builder
    public ImagePostResponseDto(PostImage entity)
    {
        this.id=entity.getId();
        this.post=entity.getPost();
        this.imageName=entity.getImageName();
        this.number=entity.getNumber();
        this.state=entity.getState();
    }
}
