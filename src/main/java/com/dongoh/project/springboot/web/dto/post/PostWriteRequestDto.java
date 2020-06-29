package com.dongoh.project.springboot.web.dto.post;

import com.dongoh.project.springboot.domain.post.Post;
import lombok.*;

@Getter
@NoArgsConstructor
public class PostWriteRequestDto {

    private Long author;//userid
    private String contents;
    private String tags;
//    private Post post;
    private Integer likes;
    private Integer replies;


    @Builder
    public PostWriteRequestDto(Long author, String contents, String tags)
    {
        this.author = author;
        this.contents = contents;
        this.tags = tags;
        this.likes=0;
        this.replies=0;
    }

    public String getString()
    {
        String result="";
        result+="author:"+author;
        result+=", content:"+contents;
        result+=", tags:"+tags;

        return result;
    }

}
