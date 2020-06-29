package com.dongoh.project.springboot.web.dto.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class SimpleNoticeMutipleRequestDto {
    String ko_title;
    String ko_contents;
    String ja_title;
    String ja_contents;
    String en_title;
    String en_contents;

    private String type;
    private int isActivated;
    private Long groupNum;

    @Builder
    SimpleNoticeMutipleRequestDto(String ko_title, String ko_contents, String ja_title, String ja_contents, String en_title, String en_contents, String type, int isActivated, Long groupNum)
    {
        this.ko_title=ko_title;
        this.ko_contents=ko_contents;
        this.en_contents=en_contents;
        this.en_title=en_title;
        this.ja_contents=ja_contents;
        this.ja_title=ja_title;
        this.type=type;
        this.isActivated=isActivated;
        this.groupNum=groupNum;
    }
}
