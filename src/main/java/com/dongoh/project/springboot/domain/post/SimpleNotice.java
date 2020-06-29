package com.dongoh.project.springboot.domain.post;

import com.dongoh.project.springboot.domain.BaseTimeEntity;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeRequestDto;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class SimpleNotice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(nullable = false)
    private boolean isActivated;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String language;

    @Column(nullable = false)
    private Long groupNum;



    @Builder
    SimpleNotice(String title, String type, String contents, boolean isActivated, String language, Long groupNum)
    {
        this.title=title;
        this.type=type;
        this.contents=contents;
        this.isActivated=isActivated;
        this.language=language;
        this.groupNum=groupNum;
    }

    public SimpleNoticeResponseDto toResponseDto()
    {
        return SimpleNoticeResponseDto.builder().entity(this).build();
    }

    public SimpleNotice update(SimpleNoticeRequestDto requestDto)
    {
        this.title=requestDto.getTitle();
        this.type=requestDto.getType();
        this.contents=requestDto.getContents();
        this.isActivated=requestDto.isActivated();
        this.language=requestDto.getLanguage();
        this.groupNum=requestDto.getGroupNum();

        return this;
    }
}
