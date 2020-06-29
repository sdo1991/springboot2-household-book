package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.message.Message;
import com.dongoh.project.springboot.domain.message.MessageRepository;
import com.dongoh.project.springboot.domain.image.PostImage;
import com.dongoh.project.springboot.domain.image.PostImageRepository;
import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostLike;
import com.dongoh.project.springboot.domain.post.PostLikeRepository;
import com.dongoh.project.springboot.domain.post.PostRepository;
import com.dongoh.project.springboot.domain.reply.Reply;
import com.dongoh.project.springboot.domain.reply.ReplyRepository;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserFollow;
import com.dongoh.project.springboot.domain.user.UserFollowRepository;
import com.dongoh.project.springboot.domain.user.UserRepository;
import com.dongoh.project.springboot.web.dto.message.MessageRequestDto;
import com.dongoh.project.springboot.web.dto.user.UserResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Service
public class FindEntityService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository postLikeRepository;
    private final MessageRepository messageRepository;
    private final UserFollowRepository userFollowRepository;

    @Transactional
    public Post findPostById(Long id)
    {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글. id:" + id));
    }

    @Transactional
    public User findUserByUserId(Long id)
    {
        User user= userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저. id:" + id));

        return user;
    }
    @Transactional
    public List<Reply> findByPost(Post post, Pageable pageable)
    {
        //return replyRepository.findReplyByPostId(id);
        return replyRepository.findByPost(post, pageable);
    }

    @Transactional
    public List<PostImage> findImagesByPostId(Long id)
    {
        return postImageRepository.findImagesByPostId(id);
    }

    @Transactional
    public List<Long> findPostLikeByPostId(Long id)
    {
        return postLikeRepository.findLikeUserByPostId(id);
    }

    @Transactional
    public Message sendMessageForPostLike(PostLike entity)
    {
        User sender=entity.getUser();
        User receiver=entity.getPost().getAuthor();
        Post post=entity.getPost();
        String url="/main?post_id="+Long.toString(entity.getPost().getId());
        String contents=sender.getName()+"님께서 "+receiver.getName()+"님의 글을 좋아합니다.";
        String type="like";

        MessageRequestDto requestDto= MessageRequestDto.builder().sender(sender).receiver(receiver).url(url).contents(contents)
                .post(post).isChecked(false).type(type).build();

        return messageRepository.save(Message.builder().requestDto(requestDto).build());
    }
    @Transactional
    public Message sendMessageForPostReply(Reply entity)
    {
        User sender=entity.getAuthor();
        User receiver=entity.getPost().getAuthor();
        Post post=entity.getPost();
        String url="/main?post_id="+Long.toString(entity.getPost().getId());
        String contents=sender.getName()+"님께서 "+receiver.getName()+"님의 글에 댓글을 달았습니다.";
        String type="reply";

        MessageRequestDto requestDto= MessageRequestDto.builder().sender(sender).receiver(receiver).url(url).contents(contents)
                .post(post).isChecked(false).type(type).build();

        return messageRepository.save(Message.builder().requestDto(requestDto).build());
    }

    @Transactional
    public Message sendMessageForFollow(UserFollow entity)
    {
        User sender=entity.getUser();
        User receiver=entity.getFollower();
        Post post=null;
        String url="/user_page?user_id="+Long.toString(entity.getUser().getId());
        String contents=sender.getName()+"님께서 "+receiver.getName()+"님을 팔로우 했습니다.";
        String type="follow";

        MessageRequestDto requestDto= MessageRequestDto.builder().sender(sender).receiver(receiver).url(url).contents(contents)
                .post(post).isChecked(false).type(type).build();

        return messageRepository.save(Message.builder().requestDto(requestDto).build());
    }

}
