package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.reply.Reply;
import com.dongoh.project.springboot.domain.reply.ReplyRepository;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.reply.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

//    private final UserService userService;
//    private final PostService postService;
    private final FindEntityService findEntityService;

    @Transactional
    public ReplyResponseDto writeReply(ReplyWriteRequestDto requestDto)
    {

        Post postEntity=findEntityService.findPostById(requestDto.getPost());
        User author=findEntityService.findUserByUserId(requestDto.getAuthor());

        Reply entity=Reply.builder()
                .contents(requestDto.getContents())
                .post(postEntity)
                .author(author)
                .likes(0)
                .build();

        Long replyId=replyRepository.save(entity).getId();
        ReplyResponseDto responseDto=ReplyResponseDto.builder().entity(entity).build();
        responseDto.setId(replyId);

        findEntityService.getPostRepository().addReplies(postEntity.getId());
        if(!entity.getAuthor().equals(entity.getPost().getAuthor()))
            findEntityService.sendMessageForPostReply(entity);

        return responseDto;
    }

    @Transactional
    public void deleteRepliesByPost(Long postId)
    {
        replyRepository.deleteRepliesByPost(postId);
    }

    @Transactional
    public ReplyResponseDto updateReply(ReplyUpdateRequestDto requestDto)
    {
//        Reply entity=Reply.builder()
        System.out.println("reply update:" +requestDto.getId()+"/"+requestDto.getContents());
        replyRepository.updateReply(requestDto.getId(), requestDto.getContents());
        return null;
    }

    @Transactional
    public ReplyResponseDto deleteReply(ReplyDeleteRequestDto requestDto)
    {
        Long postId=replyRepository.getOne(requestDto.getId()).getPost().getId();
        replyRepository.deleteById(requestDto.getId());

        findEntityService.getPostRepository().deleteReplies(postId);

        return null;
    }

    @Transactional
    public List<ReplyResponseDto> findAllRepliesByPostId(ReplyMoreRepliesLoadReqeustDto reqeustDto)
    {
        List<Reply> entityList=replyRepository.findByPost(findEntityService.findPostById(reqeustDto.getPostId()));
        List<ReplyResponseDto> result= entityList.stream().map(ReplyResponseDto::new).collect(Collectors.toList());

        for(ReplyResponseDto r:result)
        {
            if(r.getAuthor().getId()==reqeustDto.getUserId())
                r.setIsMine();
        }

        return result;
    }
}
