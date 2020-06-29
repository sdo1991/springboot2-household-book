package com.dongoh.project.springboot.web.dto.translate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TranslateRequestDto {

    private String text;
    private String sourceLanguage;
    private String targetLanguage;

    @Builder
    public TranslateRequestDto(String text, String sourceLanguage, String targetLanguage)
    {
        this.text=text;
        this.sourceLanguage=sourceLanguage;
        this.targetLanguage=targetLanguage;
    }
}
