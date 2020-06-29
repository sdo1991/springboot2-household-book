package com.dongoh.project.springboot.web.dto.notice;

import com.dongoh.project.springboot.domain.post.SimpleNotice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SimpleNoticeResponseDto {

    private String title;
    private String type;
    private String contents;
    private boolean isActivated;
    private String language;
    private Long groupNum;
    private boolean isFirst;
    private boolean isLast;

    @Builder
    SimpleNoticeResponseDto(SimpleNotice entity)
    {
        this.title=entity.getTitle();
        this.type=entity.getType();
        this.contents=entity.getContents();
        this.isActivated=entity.isActivated();
        this.language=entity.getLanguage();
        this.groupNum=entity.getGroupNum();
        if(language.equals("ko"))
            isFirst=true;
        else
            isFirst=false;
        if(language.equals("en"))
            isLast=true;
        else
            isLast=false;

    }
}
