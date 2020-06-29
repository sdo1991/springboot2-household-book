package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostLike;
import com.dongoh.project.springboot.domain.post.PostLikeRepository;
import com.dongoh.project.springboot.domain.post.PostRepository;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.post.PostLikeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final FindEntityService findEntityService;


    @Transactional
    public void deleteLikeByPost(Long postId)
    {
        postLikeRepository.deleteLikeByPost(postId);
    }

    @Transactional
    public void addLike(PostLikeRequestDto requestDto)
    {
        PostLike entity=PostLike.builder()
                .post(findEntityService.findPostById(requestDto.getPostId()))
                .user(findEntityService.findUserByUserId(requestDto.getUserId()))
                .build();
        postLikeRepository.save(entity);
        findEntityService.sendMessageForPostLike(entity);

    }

    @Transactional(readOnly = true)
    public boolean checkLike(PostLikeRequestDto requestDto)
    {
        List<Long> result=postLikeRepository.ckeckLike(requestDto.getPostId(),requestDto.getUserId());
        if(result.size()>0)
            return false;
        else
            return true;
    }
}
