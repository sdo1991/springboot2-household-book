package com.dongoh.project.springboot.service;

import com.dongoh.project.springboot.domain.image.PostImage;
import com.dongoh.project.springboot.domain.image.PostImageRepository;
import com.dongoh.project.springboot.web.dto.image.ImagePostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final PostImageRepository postImageRepository;
    private final FindEntityService findEntityService;


    @Transactional
    public void deletePostImageByPost(Long postId)
    {
        postImageRepository.deleteImagesByPostId(postId);
    }

    @Transactional
    public String uploadProfileImage(MultipartFile mf, String path)
    {
        System.out.println("profile:"+path);
        String originFileName = mf.getOriginalFilename(); // 원본 파일 명
        long fileSize = mf.getSize(); // 파일 사이즈

        System.out.println("originFileName : " + originFileName);
        System.out.println("fileSize : " + fileSize);

        String newFileName=System.currentTimeMillis() + originFileName;
        String safeFile = path + newFileName;


        try {
            mf.transferTo(new File(safeFile));
        }
        catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        return newFileName;
    }

    @Transactional
    public String uploadPostImage(ImagePostRequestDto imagePostRequestDto) {

        Long postId= imagePostRequestDto.getPost();
        List<MultipartFile> fileList = imagePostRequestDto.getImages();

        String path = imagePostRequestDto.getPath();
        System.out.println("post:"+path);

        int i=0;

        for (MultipartFile mf : fileList) {
            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
            long fileSize = mf.getSize(); // 파일 사이즈

            System.out.println("originFileName : " + originFileName);
            System.out.println("fileSize : " + fileSize);

            String newFileName=System.currentTimeMillis() + originFileName;
            String safeFile = path + newFileName;
            try {
                mf.transferTo(new File(safeFile));
                PostImage entity;
                if(i==0) {
                    entity = PostImage.builder()
                            .post(findEntityService.findPostById(postId))
                            .imageName(newFileName)
                            .number(i++)
                            .state("active")
                            .build();
                }
                else{
                    entity = PostImage.builder()
                        .post(findEntityService.findPostById(postId))
                        .imageName(newFileName)
                        .number(i++)
                        .build();
                }

                postImageRepository.save(entity);
                System.out.println("file path : " + safeFile);

            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "redirect:/";
    }
}
