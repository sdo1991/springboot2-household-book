package com.dongoh.project.springboot.web.dto.message;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReportRequestDto {

    private Long postId;
    private Long reportedUserId;
    private Long reporterUserId;
    private String contents;
    private String category;
    private String target;
    private Long reportedReplyId;


    @Builder
    ReportRequestDto(Long postId, Long reportedUserId, Long reporterUserId, String contents, String category, String target, Long reportedReplyId)
    {
        this.postId=postId;
        this.reportedUserId=reportedUserId;
        this.reporterUserId=reporterUserId;
        this.contents=contents;
        this.category=category;
        this.target=target;
        this.reportedReplyId=reportedReplyId;
    }
}
