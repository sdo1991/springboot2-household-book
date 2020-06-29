package com.dongoh.project.springboot.web.dto.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
public class SimpleNoticeRequestDto {

    private String title;
    private String type;
    private String contents;
    private boolean isActivated;
    private String language;
    private Long groupNum;

    @Builder
    SimpleNoticeRequestDto(String title, String type, String contents, boolean isActivated, String language, Long groupNum)
    {
        this.title=title;
        this.type=type;
        this.contents=contents;
        this.isActivated=isActivated;
        this.language=language;
        this.groupNum=groupNum;
    }

}
