package com.dongoh.project.springboot.domain.image;

import com.dongoh.project.springboot.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Query(value = "select * from post_image i where i.post = :post", nativeQuery = true)
    List<PostImage> findImagesByPostId(@Param("post") Long post);

    @Modifying
    @Query(value = "delete from post_image i where i.post = :post", nativeQuery = true)
    void deleteImagesByPostId(@Param("post") Long post);


}
