package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.post.PostLike;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserFollow;
import com.dongoh.project.springboot.domain.user.UserFollowRepository;
import com.dongoh.project.springboot.web.dto.post.PostLikeRequestDto;
import com.dongoh.project.springboot.web.dto.user.FollowRequestDto;
import com.dongoh.project.springboot.web.dto.user.FollowerListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserFollowService {
    private final UserFollowRepository userFollowRepository;
    private final FindEntityService findEntityService;


    @Transactional(readOnly = true)
    public List<FollowerListResponseDto> findFollowerByUser(User user)
    {
        List<UserFollow> entityList=userFollowRepository.findByUser(user);
        List<FollowerListResponseDto> result=new ArrayList<>();
        for(UserFollow uf:entityList)
        {
            result.add(FollowerListResponseDto.builder().userEntity(uf.getFollower()).build());
        }
        return result;
    }

    @Transactional
    public void addFollow(FollowRequestDto requestDto)
    {
        UserFollow entity=UserFollow.builder()
                .user(findEntityService.findUserByUserId(requestDto.getSendUserId()))
                .follower(findEntityService.findUserByUserId(requestDto.getReceiveUserId()))
                .build();
        userFollowRepository.save(entity);
        findEntityService.sendMessageForFollow(entity);

    }

    @Transactional
    public void putFollow(UserFollow entity)
    {/*
        UserFollow entity=UserFollow.builder()
                .user(findEntityService.findUserByUserId(requestDto.getSendUserId()))
                .follower(findEntityService.findUserByUserId(requestDto.getReceiveUserId()))
                .build();
       */
        userFollowRepository.delete(entity);

//        findEntityService.sendMessageForFollow(entity);

    }

    @Transactional(readOnly = true)
    public UserFollow findByUserAndFollower(FollowRequestDto requestDto)
    {
        return userFollowRepository
                .findByUserAndFollower(findEntityService.findUserByUserId(requestDto.getSendUserId())
                        ,findEntityService.findUserByUserId(requestDto.getReceiveUserId()));
    }

    @Transactional(readOnly = true)
    public boolean checkFollow(FollowRequestDto requestDto)
    {
        Integer result=userFollowRepository
                .countByUserAndFollower(findEntityService.findUserByUserId(requestDto.getSendUserId())
                        ,findEntityService.findUserByUserId(requestDto.getReceiveUserId()));
        if(result>0)
            return false;
        else
            return true;
    }
}
