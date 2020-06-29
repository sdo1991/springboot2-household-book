package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.post.SimpleNotice;
import com.dongoh.project.springboot.domain.post.SimpleNoticeRepository;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeMutipleRequestDto;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeRequestDto;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeResponseDto;
import com.dongoh.project.springboot.web.dto.post.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SimpleNoticeService {
    private final SimpleNoticeRepository simpleNoticeRepository;
    private final FindEntityService findEntityService;

    @Transactional
    public void updateNotice(SimpleNoticeMutipleRequestDto requestDto)
    {
        boolean flag=true;
        if(requestDto.getIsActivated()==0)
            flag=false;

        SimpleNoticeRequestDto koDto=
                SimpleNoticeRequestDto.builder()
                        .title(requestDto.getKo_title())
                        .contents(requestDto.getKo_contents())
                        .language("ko")
                        .isActivated(flag)
                        .groupNum(requestDto.getGroupNum())
                        .type(requestDto.getType())
                        .build();
        SimpleNoticeRequestDto jaDto=
                SimpleNoticeRequestDto.builder()
                        .title(requestDto.getJa_title())
                        .contents(requestDto.getJa_contents())
                        .language("ja")
                        .isActivated(flag)
                        .groupNum(requestDto.getGroupNum())
                        .type(requestDto.getType())
                        .build();
        SimpleNoticeRequestDto enDto=
                SimpleNoticeRequestDto.builder()
                        .title(requestDto.getEn_title())
                        .contents(requestDto.getEn_contents())
                        .language("en")
                        .isActivated(flag)
                        .groupNum(requestDto.getGroupNum())
                        .type(requestDto.getType())
                        .build();

        SimpleNoticeRequestDto[] requestDtos= {koDto, jaDto, enDto};
        for(SimpleNoticeRequestDto dto:requestDtos)
        {
            SimpleNotice entity=simpleNoticeRepository.findByGroupNumAndLanguage(dto.getGroupNum(),dto.getLanguage());
            simpleNoticeRepository.save(entity.update(dto));
        }


    }

    @Transactional
    public void saveDefaultNotice(SimpleNoticeRequestDto requestDto)
    {
        SimpleNotice entity=SimpleNotice.builder().contents(requestDto.getContents()).isActivated(requestDto.isActivated())
                .title(requestDto.getTitle()).type(requestDto.getType()).groupNum(requestDto.getGroupNum()).language(requestDto.getLanguage()).build();
        simpleNoticeRepository.save(entity);
    }

    @Transactional
    public List<SimpleNoticeResponseDto> findAllNotice()
    {

        List<SimpleNotice> entityList= simpleNoticeRepository.findAll();

        List<SimpleNoticeResponseDto> result=new ArrayList<SimpleNoticeResponseDto>();

        for(SimpleNotice entity:entityList)
        {
            result.add(entity.toResponseDto());
        }

        return result;
    }

    @Transactional
    public List<SimpleNoticeResponseDto> findNoticeByLanguage(String language)
    {
        List<SimpleNotice> entityList= simpleNoticeRepository.findByLanguageAndIsActivated(language, true);

        List<SimpleNoticeResponseDto> result=new ArrayList<SimpleNoticeResponseDto>();

        for(SimpleNotice entity:entityList)
        {
          result.add(entity.toResponseDto());
        }

        return result;
    }


}
