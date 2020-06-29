package com.dongoh.project.springboot.web.dto.image;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ImagePostRequestDto {

    private Long post;
    private List<MultipartFile> images;
    private String path;

    @Builder
    public ImagePostRequestDto(Long post, List<MultipartFile> images, String path)
    {
        this.post=post;
        this.images=images;
        this.path=path;
    }

}
