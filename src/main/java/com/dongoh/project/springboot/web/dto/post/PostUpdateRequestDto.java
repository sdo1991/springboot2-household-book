package com.dongoh.project.springboot.web.dto.post;

import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private Long id;
//    private User author;
    private String contents;
    private String tags;

    @Builder
    public PostUpdateRequestDto(Long id, String contents, String tags)
    {
        this.id=id;
        this.contents=contents;
        this.tags=tags;
    }
}
