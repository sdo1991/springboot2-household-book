package com.dongoh.project.springboot.service;


import com.dongoh.project.springboot.domain.message.Message;
import com.dongoh.project.springboot.domain.message.MessageRepository;
import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.message.AlarmResponseDto;
import com.dongoh.project.springboot.web.dto.message.MessageRequestDto;
import com.dongoh.project.springboot.web.dto.message.MessageResponseDto;
import com.dongoh.project.springboot.web.dto.message.ReportRequestDto;
import com.dongoh.project.springboot.web.dto.post.PostResponseDto;
import lombok.Getter;
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
@Getter
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final FindEntityService findEntityService;

    @Transactional
    public void deleteMessageByPostId(Long postId)
    {
        messageRepository.deleteByPost(findEntityService.findPostById(postId));
    }

    @Transactional
    public void setIsCheckedTrue(User receiver)
    {
        messageRepository.updateIsCheckedByUserId(receiver.getId());
    }

    @Transactional(readOnly = true)
    public Integer countMessageByUserId(User user)
    {
        return messageRepository.countByReceiver(user);
    }
    @Transactional(readOnly = true)
    public Integer countMessageByType(String type)
    {
        return messageRepository.countByType(type);
    }

    @Transactional
    public void sendReport(ReportRequestDto requestDto)
    {
        User sender=findEntityService.findUserByUserId(requestDto.getReporterUserId());
        User receiver=findEntityService.findUserByUserId(requestDto.getReportedUserId());
        Post post=findEntityService.findPostById(requestDto.getPostId());
        String url=null;
        String contents=null;
        String type=null;

        if(requestDto.getTarget().equals("post")) {
             contents = sender.getName() + "님께서 " + receiver.getName() + "님의 글을 신고했습니다.";
            url = "/main?post_id=" + Long.toString(requestDto.getPostId())+"&reported=yes";
        }
        else if(requestDto.getTarget().equals("reply")) {
            contents = sender.getName() + "님께서 " + receiver.getName() + "님의 댓글을 신고했습니다.";
            url = "/main?post_id=" + Long.toString(requestDto.getPostId())+"&report_reply="+requestDto.getReportedReplyId();
        }
        contents+="\n분류:"+requestDto.getCategory()+"\n내용:"+requestDto.getContents();
        type = "report";

        MessageRequestDto messageRequestDto= MessageRequestDto.builder().sender(sender).receiver(receiver).url(url).contents(contents)
                .post(post).isChecked(false).type(type).build();
        messageRepository.save(Message.builder().requestDto(messageRequestDto).build());
    }


    @Transactional(readOnly = true)
    public AlarmResponseDto findAlarmMessageByUserId(User user)
    {
//        final int messagePageCount = 3;

//        Pageable messagePageable = PageRequest.of(0, messagePageCount, Sort.by("id").descending());

        List<Message> messagesEntity=messageRepository.findByReceiverAndIsCheckedAndTypeNot(user,false,"report");
        List<MessageResponseDto> message = new ArrayList<MessageResponseDto>();

        for(Message m: messagesEntity)
        {
            message.add(MessageResponseDto.builder().entity(m).build());
        }

        System.out.println("alarm:"+message.size());

        return AlarmResponseDto.builder().messages(message).build();
    }


    @Transactional(readOnly = true)
    public List<MessageResponseDto> findAllReports(User user, Integer page)
    {
        final int messagePageCount = 10;
        Pageable messagePageable = PageRequest.of(page-1, messagePageCount, Sort.by("id").descending());



        List<Message> messagesEntity=messageRepository.findByType("report", messagePageable);
        List<MessageResponseDto> message = new ArrayList<MessageResponseDto>();

        for(Message m: messagesEntity)
        {
            message.add(MessageResponseDto.builder().entity(m).build());
        }

        System.out.println(user.getId());

        return message;
    }
    @Transactional(readOnly = true)
    public List<MessageResponseDto> findMessageByUserId(User user, Integer page)
    {
        final int messagePageCount = 10;
        Pageable messagePageable = PageRequest.of(page-1, messagePageCount, Sort.by("id").descending());



        List<Message> messagesEntity=messageRepository.findByReceiverAndTypeNot(user,"report", messagePageable);
        List<MessageResponseDto> message = new ArrayList<MessageResponseDto>();

        for(Message m: messagesEntity)
        {
            message.add(MessageResponseDto.builder().entity(m).build());
        }

        System.out.println(user.getId());

        return message;
    }

}
