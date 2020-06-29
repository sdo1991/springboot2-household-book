package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.service.TranslateService;
import com.dongoh.project.springboot.web.dto.translate.TranslateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TranslateApiController {

    private final TranslateService translateService;

    @PostMapping("/api/translate")
    public String translateText(@RequestBody TranslateRequestDto requestDto)
    {
        System.out.println("text:"+requestDto.getText());
        System.out.println("sorce:"+requestDto.getSourceLanguage());
        System.out.println("target:"+requestDto.getTargetLanguage());

        if(requestDto.getTargetLanguage().equals(requestDto.getSourceLanguage()))
            return requestDto.getText();

        String result=translateService.translateText(requestDto.getText(),requestDto.getSourceLanguage(),requestDto.getTargetLanguage());

        System.out.println(result);
        result=result.replace("\n","</br>");

        System.out.println(result);

        return result;
    }

}
