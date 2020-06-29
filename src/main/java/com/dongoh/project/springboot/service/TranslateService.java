package com.dongoh.project.springboot.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import com.google.cloud.translate.testing.RemoteTranslateHelper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TranslateService {

    private static Translate translate;
/*
    private static final String[] LANGUAGES = {
            "af", "sq", "ar", "hy", "az", "eu", "be", "bn", "bs", "bg", "ca", "ceb", "ny", "zh-TW", "hr",
            "cs", "da", "nl", "en", "eo", "et", "tl", "fi", "fr", "gl", "ka", "de", "el", "gu", "ht", "ha",
            "iw", "hi", "hmn", "hu", "is", "ig", "id", "ga", "it", "ja", "jw", "kn", "kk", "km", "ko", "lo",
            "la", "lv", "lt", "mk", "mg", "ms", "ml", "mt", "mi", "mr", "mn", "my", "ne", "no", "fa", "pl",
            "pt", "ro", "ru", "sr", "st", "si", "sk", "sl", "so", "es", "su", "sw", "sv", "tg", "ta", "te",
            "th", "tr", "uk", "ur", "uz", "vi", "cy", "yi", "yo", "zu"
    };
*/

    public TranslateService() {
        RemoteTranslateHelper helper = RemoteTranslateHelper.create();
        translate = helper.getOptions().getService();
    }

    public String translateText(String text, String sourceLanguage, String targetLanguage)
    {
        List<String> texts = new LinkedList<>();

        String[] multiText=text.split("\n");
        for(String s:multiText)
            texts.add(s);
        List<Translation> translations =
                translate.translate(
                        texts,
                        Translate.TranslateOption.sourceLanguage(sourceLanguage),
                        Translate.TranslateOption.targetLanguage(targetLanguage));

        String translatedLine="";

        for(Translation t: translations)
            translatedLine += t.getTranslatedText() + "\n";


        translatedLine=translatedLine.substring(0,translatedLine.length()-1);

        return translatedLine;
    }

}

