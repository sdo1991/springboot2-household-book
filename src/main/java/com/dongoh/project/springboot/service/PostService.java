package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.post.PostLike;
import com.dongoh.project.springboot.domain.post.PostRepository;
import com.dongoh.project.springboot.domain.reply.Reply;
import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.image.ImagePostRequestDto;
import com.dongoh.project.springboot.web.dto.image.ImagePostResponseDto;
import com.dongoh.project.springboot.web.dto.post.*;
import com.dongoh.project.springboot.web.dto.reply.ReplyResponseDto;
import com.dongoh.project.springboot.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final FindEntityService findEntityService;

    @Transactional
    public Long writePost(PostWriteRequestDto postWriteRequestDto) {

        Post entity=Post.builder()
                .author(findEntityService.findUserByUserId(postWriteRequestDto.getAuthor()))
                .contents(postWriteRequestDto.getContents())
                .tags(postWriteRequestDto.getTags())
                .likes(postWriteRequestDto.getLikes())
                .replies(postWriteRequestDto.getReplies())
                .build();

        postRepository.save(entity);

        return entity.getId();
    }

    @Transactional
    public void updatePost(PostUpdateRequestDto requestDto) {
        postRepository.updatePost(requestDto.getContents(),requestDto.getTags(),requestDto.getId());

    }

    @Transactional
    public void deletePost(PostDeleteRequestDto requestDto) {
        postRepository.delete(findEntityService.findPostById(requestDto.getPostId()));
    }

    @Transactional(readOnly = true)//조회기능만 남겨두어 조회속도가 개선됨
    public List<PostReadDto> findPostsByTag(User user, Integer page, String tag){

        final int postPageCount = 10;
        Pageable postPageable = PageRequest.of(page, postPageCount, Sort.by("id").descending());
        String[] interestLang=user.getInterestLang().split(",");
        List<PostResponseDto> post = postRepository
                .findPostByTags("#"+tag+"%",interestLang,user.getMainLang(),user.getId(),postPageable)
                .stream().map(PostResponseDto::new).collect(Collectors.toList());//포스트 조회(조건X)

        List<PostReadDto> postReadDtos = new ArrayList<PostReadDto>();

        for(PostResponseDto p:post)
        {
            final int replyPageCount=3;
            Pageable replyPageable=PageRequest.of(0, replyPageCount, Sort.by("id").descending());//리플 페이지

            Post postEntity=postRepository.getOne(p.getId());

            List<ReplyResponseDto> replyList
                    =findEntityService
                    .findByPost(postEntity, replyPageable)
                    .stream().map(ReplyResponseDto::new).collect(Collectors.toList());//포스트별 리플 조회

            Integer remainingReplies=p.getReplies()-replyPageCount;



            List<ImagePostResponseDto> imageList
                    =findEntityService
                    .findImagesByPostId(p.getId())
                    .stream()
                    .map(ImagePostResponseDto::new)
                    .collect(Collectors.toList());


            UserResponseDto author=findEntityService.findUserByUserId(p.getAuthor().getId()).toResponseDto();
            List<Long> likeUsers=findEntityService.findPostLikeByPostId(p.getId());

            String isLike="far";
            for(Long u:likeUsers)
            {
                if(u==user.getId())
                {
                    isLike="fas";
                    break;
                }
            }
            p.setIsLike(isLike);

            if(author.getId()==user.getId())
                p.setIsMine();

            for(ReplyResponseDto r: replyList)
            {
                if(r.getAuthor().getId()==user.getId())
                    r.setIsMine();
            }

            postReadDtos.add(PostReadDto.builder().post(p).replies(replyList).author(author).images(imageList).remainReplies(remainingReplies).build());

//            System.out.println(replyList.get(0).getId());
        }
        System.out.println("loaded post count:"+postReadDtos.size());
        return postReadDtos;

    }



    @Transactional(readOnly = true)//조회기능만 남겨두어 조회속도가 개선됨
    public List<PostReadDto> findUserInfoPost(User user, Integer page, String userId){

        final int postPageCount = 10;
        Pageable postPageable = PageRequest.of(page, postPageCount, Sort.by("id").descending());
        List<PostResponseDto> post=null;
        User userEntity=findEntityService.findUserByUserId(Long.parseLong(userId));

        post = postRepository
                    .findByAuthor(userEntity,postPageable)
                    .stream().map(PostResponseDto::new).collect(Collectors.toList());//포스트 조회(조건X)

        List<PostReadDto> postReadDtos = new ArrayList<PostReadDto>();

        for(PostResponseDto p:post)
        {
            final int replyPageCount=3;
            Pageable replyPageable=PageRequest.of(0, replyPageCount, Sort.by("id").descending());//리플 페이지

            Post postEntity=postRepository.getOne(p.getId());

            List<ReplyResponseDto> replyList
                    =findEntityService
                    .findByPost(postEntity, replyPageable)
                    .stream().map(ReplyResponseDto::new).collect(Collectors.toList());//포스트별 리플 조회

            Integer remainingReplies=p.getReplies()-replyPageCount;



            List<ImagePostResponseDto> imageList
                    =findEntityService
                    .findImagesByPostId(p.getId())
                    .stream()
                    .map(ImagePostResponseDto::new)
                    .collect(Collectors.toList());


            UserResponseDto author=findEntityService.findUserByUserId(p.getAuthor().getId()).toResponseDto();
            List<Long> likeUsers=findEntityService.findPostLikeByPostId(p.getId());

            String isLike="far";
            for(Long u:likeUsers)
            {
                if(u==user.getId())
                {
                    isLike="fas";
                    break;
                }
            }
            p.setIsLike(isLike);

            if(author.getId()==user.getId())
                p.setIsMine();

            for(ReplyResponseDto r: replyList)
            {
                if(r.getAuthor().getId()==user.getId())
                    r.setIsMine();
            }

            postReadDtos.add(PostReadDto.builder().post(p).replies(replyList).author(author).images(imageList).remainReplies(remainingReplies).build());

//            System.out.println(replyList.get(0).getId());
        }
        System.out.println("loaded post count:"+postReadDtos.size());
        return postReadDtos;

    }


    @Transactional(readOnly = true)//조회기능만 남겨두어 조회속도가 개선됨
    public List<PostReadDto> findPostsForFollowers(User user, Integer page, Long[] followers){

        final int postPageCount = 10;
        Pageable postPageable = PageRequest.of(page, postPageCount, Sort.by("id").descending());
        List<PostResponseDto> post=null;
            String[] interestLang=user.getInterestLang().split(",");

            for(String s:interestLang)
                System.out.println("langs:"+s);

            post = postRepository
                    .findPostForFollowers(user.getId(), followers ,postPageable)
                    .stream().map(PostResponseDto::new).collect(Collectors.toList());//포스트 조회(조건X)


        List<PostReadDto> postReadDtos = new ArrayList<PostReadDto>();

        for(PostResponseDto p:post)
        {
            final int replyPageCount=3;
            Pageable replyPageable=null;
                replyPageable=PageRequest.of(0, replyPageCount, Sort.by("id").ascending());//리플 페이지

            Post postEntity=postRepository.getOne(p.getId());

            List<ReplyResponseDto> replyList
                    =findEntityService
                    .findByPost(postEntity, replyPageable)
                    .stream().map(ReplyResponseDto::new).collect(Collectors.toList());//포스트별 리플 조회

            Integer remainingReplies=0;
                remainingReplies=p.getReplies()-replyPageCount;



            List<ImagePostResponseDto> imageList
                    =findEntityService
                    .findImagesByPostId(p.getId())
                    .stream()
                    .map(ImagePostResponseDto::new)
                    .collect(Collectors.toList());


            UserResponseDto author=findEntityService.findUserByUserId(p.getAuthor().getId()).toResponseDto();
            List<Long> likeUsers=findEntityService.findPostLikeByPostId(p.getId());

            String isLike="far";
            for(Long u:likeUsers)
            {
                if(u==user.getId())
                {
                    isLike="fas";
                    break;
                }
            }
            p.setIsLike(isLike);

            if(author.getId()==user.getId()||user.getRole().getKey().equals(Role.ADMIN.getKey()))
                p.setIsMine();

            for(ReplyResponseDto r: replyList)
            {
                if(r.getAuthor().getId()==user.getId()||user.getRole().getKey().equals(Role.ADMIN.getKey()))
                    r.setIsMine();
            }

            postReadDtos.add(PostReadDto.builder().post(p).replies(replyList).author(author).images(imageList).remainReplies(remainingReplies).build());

//            System.out.println(replyList.get(0).getId());
        }
        System.out.println("loaded post count:"+postReadDtos.size());
        return postReadDtos;

    }



    @Transactional(readOnly = true)//조회기능만 남겨두어 조회속도가 개선됨
    public List<PostReadDto> findPosts(User user, Integer page, String postId, boolean isAdminPage){

        final int postPageCount = 10;
        Pageable postPageable = PageRequest.of(page, postPageCount, Sort.by("id").descending());
        List<PostResponseDto> post=null;
        if(postId==null) {
            String[] interestLang=user.getInterestLang().split(",");

            for(String s:interestLang)
                System.out.println("langs:"+s);

            if(isAdminPage)
            {
                post = postRepository
                        .findAll(postPageable)
                        .stream().map(PostResponseDto::new).collect(Collectors.toList());//포스트 조회(조건X)
            }
            else {
                post = postRepository
                        .findAllPost(interestLang, user.getMainLang(), user.getId(), postPageable)
                        .stream().map(PostResponseDto::new).collect(Collectors.toList());//포스트 조회(조건X)
            }
        }
        else
        {
            post=new ArrayList<PostResponseDto>();
            Post postEntity=postRepository.findById(Long.parseLong(postId))
                    .orElseThrow(()->new IllegalArgumentException("존재하지 않는 포스트"));
            post.add(PostResponseDto.builder().entity(postEntity).build());
        }

            List<PostReadDto> postReadDtos = new ArrayList<PostReadDto>();

        for(PostResponseDto p:post)
        {
            final int replyPageCount=3;
            Pageable replyPageable=null;
            if(postId==null)
                replyPageable=PageRequest.of(0, replyPageCount, Sort.by("id").ascending());//리플 페이지

            Post postEntity=postRepository.getOne(p.getId());

            List<ReplyResponseDto> replyList
                    =findEntityService
                    .findByPost(postEntity, replyPageable)
                    .stream().map(ReplyResponseDto::new).collect(Collectors.toList());//포스트별 리플 조회

            Integer remainingReplies=0;
            if(postId==null)
                remainingReplies=p.getReplies()-replyPageCount;



            List<ImagePostResponseDto> imageList
                    =findEntityService
                    .findImagesByPostId(p.getId())
                    .stream()
                    .map(ImagePostResponseDto::new)
                    .collect(Collectors.toList());


            UserResponseDto author=findEntityService.findUserByUserId(p.getAuthor().getId()).toResponseDto();
            List<Long> likeUsers=findEntityService.findPostLikeByPostId(p.getId());

            String isLike="far";
            for(Long u:likeUsers)
            {
                if(u==user.getId())
                {
                    isLike="fas";
                    break;
                }
            }
            p.setIsLike(isLike);

            if(author.getId()==user.getId()||user.getRole().getKey().equals(Role.ADMIN.getKey()))
                p.setIsMine();

            for(ReplyResponseDto r: replyList)
            {
                if(r.getAuthor().getId()==user.getId()||user.getRole().getKey().equals(Role.ADMIN.getKey()))
                    r.setIsMine();
            }

            postReadDtos.add(PostReadDto.builder().post(p).replies(replyList).author(author).images(imageList).remainReplies(remainingReplies).build());

//            System.out.println(replyList.get(0).getId());
        }
        System.out.println("loaded post count:"+postReadDtos.size());
        return postReadDtos;
    }

    @Transactional
    public void addLike(PostLikeRequestDto requestDto)
    {
        postRepository.addLike(requestDto.getPostId());
    }

}
