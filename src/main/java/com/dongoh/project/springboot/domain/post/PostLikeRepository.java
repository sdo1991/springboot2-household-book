package com.dongoh.project.springboot.domain.post;

import com.dongoh.project.springboot.domain.reply.Reply;
import com.dongoh.project.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {


    @Query(value = "select user from post_like p where p.post = :post", nativeQuery = true)
    List<Long> findLikeUserByPostId(@Param("post") Long post);

    @Query(value = "select * from post_like p where p.post = :post and p.user= :user", nativeQuery = true)
    List<Long> ckeckLike(@Param("post") Long post, @Param("user") Long user);


    @Modifying
    @Query(value = "delete from post_like p where p.post = :post", nativeQuery = true)
    void deleteLikeByPost(@Param("post") Long post);


}
