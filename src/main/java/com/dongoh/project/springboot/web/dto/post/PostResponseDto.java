package com.dongoh.project.springboot.web.dto.post;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseDto {

    private Long id;
    private User author;
    //private Long authorId로 바꿔야함!
    private String contents;
    private List<String> tags;
    private Integer likes;
    private Integer replies;
    private String isLike;
    private Boolean isMine;



    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    @Builder
    public PostResponseDto(Post entity)
    {
        this.id=entity.getId();
        this.author=entity.getAuthor();

        this.contents=entity.getContents().replace("\n", "\n<br>");

        String[] temp=entity.getTags().split("#");
        this.tags=new ArrayList<String>();
        for(int i=1;i<temp.length;i++)
            this.tags.add(temp[i]);

        this.likes=entity.getLikes();
        this.replies=entity.getReplies();

        this.createdDate=entity.getCreatedDate();
        this.modifiedDate=entity.getModifiedDate();

        this.isMine=false;

//        System.out.println(this.isMine);
    }

    public void setIsLike(String isLike)
    {
        this.isLike=isLike;
    }
    public void setIsMine()
    {
        this.isMine=true;
    }

}
