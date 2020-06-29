package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.service.FindEntityService;
import com.dongoh.project.springboot.service.ReplyService;
import com.dongoh.project.springboot.web.dto.reply.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReplyApiController {
    private final ReplyService replyService;
    private final FindEntityService findEntityService;

    @PostMapping("/api/reply")
    public ReplyResponseDto writeReply(@RequestBody ReplyWriteRequestDto requestDto)
    {
        return replyService.writeReply(requestDto);
    }

    @PutMapping("/api/reply")
    public ReplyResponseDto updateReply(@RequestBody ReplyUpdateRequestDto requestDto){
        return replyService.updateReply(requestDto);

    }

    @DeleteMapping("/api/reply")
    public ReplyResponseDto deleteReply(@RequestBody ReplyDeleteRequestDto requestDto)
    {
        return replyService.deleteReply(requestDto);
    }

    @PostMapping("/api/post/load_all_replies")
    public ReplyLoadAllResponseDto loadAllReplies(@RequestBody ReplyMoreRepliesLoadReqeustDto reqeustDto)
    {
        System.out.println("post:"+reqeustDto.getPostId());
        System.out.println("user:"+reqeustDto.getUserId());
        List<ReplyResponseDto> replies= replyService.findAllRepliesByPostId(reqeustDto);
        Integer number=findEntityService.getPostRepository().findRepliesNumberById(reqeustDto.getPostId());

        return ReplyLoadAllResponseDto.builder().number(number).replies(replies).build();
    }


}
