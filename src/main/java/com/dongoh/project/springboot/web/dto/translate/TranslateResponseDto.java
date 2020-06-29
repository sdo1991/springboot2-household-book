package com.dongoh.project.springboot.web.dto.translate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TranslateResponseDto {

    String translatedText;
    String sourceLanguage;
    String targetLanguage;

}
