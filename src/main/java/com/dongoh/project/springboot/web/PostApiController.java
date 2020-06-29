package com.dongoh.project.springboot.web;

import com.dongoh.project.springboot.domain.post.PostLike;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.service.*;
import com.dongoh.project.springboot.web.dto.image.ImagePostRequestDto;
import com.dongoh.project.springboot.web.dto.message.MessageCheckRequestDto;
import com.dongoh.project.springboot.web.dto.message.ReportRequestDto;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeMutipleRequestDto;
import com.dongoh.project.springboot.web.dto.notice.SimpleNoticeRequestDto;
import com.dongoh.project.springboot.web.dto.post.*;
import com.dongoh.project.springboot.web.dto.user.FollowRequestDto;
import com.dongoh.project.springboot.web.dto.user.FollowerListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final ImageService imageService;
    private final ReplyService replyService;
    private final FindEntityService findEntityService;
    private final MessageService messageService;
    private final SimpleNoticeService simpleNoticeService;
    private final UserFollowService userFollowService;


    @PutMapping("/api/notice")
    public void updateNotices(@RequestBody SimpleNoticeMutipleRequestDto requestDto)
    {

        simpleNoticeService.updateNotice(requestDto);

    }

    @PutMapping("/api/checkMessage")
    public void checkMessage(@RequestBody MessageCheckRequestDto requestDto)
    {
        messageService.setIsCheckedTrue(findEntityService.findUserByUserId(requestDto.getUserId()));
    }

    @PostMapping("/api/report")
    public void sendReport(@RequestBody ReportRequestDto requestDto)
    {
        messageService.sendReport(requestDto);

    }


    @PostMapping("/api/post")
    public Long writePost(MultipartHttpServletRequest mtfRequest) {

        PostWriteRequestDto requestDto = PostWriteRequestDto.builder()
                .author(Long.parseLong(mtfRequest.getParameter("write_author")))
                .contents(mtfRequest.getParameter("write_contents"))
                .tags(mtfRequest.getParameter("tags"))
                .build();

        Long postId = postService.writePost(requestDto);

        if (mtfRequest.getFiles("write_images").get(0).getSize() > 0) {
            imageService.uploadPostImage(ImagePostRequestDto.builder()
                    .images(mtfRequest.getFiles("write_images"))
                    .post(postId)
                    .path(mtfRequest.getSession().getServletContext().getRealPath("/"))
                    .build());
        }

        return postId;
    }

    @PutMapping("/api/post")
    public void updatePost(@RequestBody PostUpdateRequestDto requestDto) {
        System.out.println(requestDto.getId());
        postService.updatePost(requestDto);
    }

    @DeleteMapping("/api/post")
    public void deletePost(@RequestBody PostDeleteRequestDto requestDto) {
        postLikeService.deleteLikeByPost(requestDto.getPostId());
        replyService.deleteRepliesByPost(requestDto.getPostId());
        imageService.deletePostImageByPost(requestDto.getPostId());
        messageService.deleteMessageByPostId(requestDto.getPostId());
        postService.deletePost(requestDto);
    }



    @PostMapping("/api/post/add_like")
    public boolean addLike(@RequestBody PostLikeRequestDto requestDto) {
        boolean result = false;

        if (postLikeService.checkLike(requestDto)) {
            postLikeService.addLike(requestDto);
            postService.addLike(requestDto);

            result = true;
        }

        return result;
    }

    @PostMapping("/api/post/add_follower")
    public boolean addFollower(@RequestBody FollowRequestDto requestDto) {
        boolean result = false;

        if (userFollowService.checkFollow(requestDto)) {
            userFollowService.addFollow(requestDto);
            //postService.addLike(requestDto);

            result = true;
        }

        return result;
    }

    @PostMapping("/api/post/load")
    public List<PostReadDto> loadPost(@RequestBody PostLoadRequestDto requestDto) {
        System.out.println(requestDto.getPage() + 1);
        System.out.println(requestDto.getUserId());
        User loginUser = findEntityService.findUserByUserId(requestDto.getUserId());

        System.out.println(requestDto.getUrl());
        System.out.println(requestDto.getUrl());
        String requestUrl = requestDto.getUrl().split("http://")[1];
        requestUrl = requestUrl.split("/", 2)[1];

        String[] requestUrlArr = requestUrl.split("\\?", 2);

        requestUrl = requestUrlArr[0];
        System.out.println(requestUrl);

        List<PostReadDto> result=null;

        if(requestUrl.equals("user_page"))
        {
            String userId=requestDto.getUrl().split("\\?user_id=")[1];
            result=postService.findUserInfoPost(loginUser, requestDto.getPage()+1, userId);
        }
        else if(requestUrl.equals("tag_page"))
        {
            String tag=requestDto.getUrl().split("\\?tag=")[1];
            result=postService.findPostsByTag(loginUser, requestDto.getPage()+1, tag);
        }
        else if(requestUrl.equals("main"))
        {
            String postId=null;
            String[] urlSplit=requestDto.getUrl().split("\\?post_id=");
            if(urlSplit.length>1)
                postId=urlSplit[1];
            result=postService.findPosts(loginUser, requestDto.getPage()+1, postId, false);
        }

        else if(requestUrl.equals("admin_post_page"))
        {
            String postId=null;
            String[] urlSplit=requestDto.getUrl().split("\\?post_id=");
            if(urlSplit.length>1)
                postId=urlSplit[1];
            result=postService.findPosts(loginUser, requestDto.getPage()+1, postId, true);
        }
        else if(requestUrl.equals("follower_page"))
        {
            List<FollowerListResponseDto> followersDto= userFollowService.findFollowerByUser(loginUser);
            Long[] followers=new Long[followersDto.size()];
            for(int i=0;i<followers.length;i++)
                followers[i]=followersDto.get(i).getUserId();

            result=postService.findPostsForFollowers(loginUser,requestDto.getPage()+1,followers);
        }
        else
        {
            System.out.println("errrr");

        }

        return result;
    }
}
