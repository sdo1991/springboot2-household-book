package com.dongoh.project.springboot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslateServiceTest {

    @Autowired
    TranslateService translateService;


    @Test
    public void testTransService()
    {
        String originText="안녕하세요";
        String expectedText="こんにちは";

        String sourceLanguage="ko";
        String targetLanguage="ja";

        String result=translateService.translateText(originText, sourceLanguage, targetLanguage);
        System.out.println(result);
        assertEquals(expectedText, result);
    }

}
