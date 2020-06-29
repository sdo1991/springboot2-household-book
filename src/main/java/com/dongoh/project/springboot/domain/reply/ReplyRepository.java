package com.dongoh.project.springboot.domain.reply;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.web.dto.reply.ReplyResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
//    @Query("SELECT r FROM Reply r  ORDER BY p.id DESC")//SpringDataJpa에서 지원하지 않는 메소드를 작성하기 위해 사용함

    @Query(value = "select * from reply r where r.post = :post", nativeQuery = true)
    List<Reply> findReplyByPostId(@Param("post") Long post);

    List<Reply> findByPost(Post post, Pageable pageable);
    List<Reply> findByPost(Post post);

    @Modifying
    @Query(value = "delete from reply r where r.post = :post", nativeQuery = true)
    void deleteRepliesByPost(@Param("post") Long post);

    @Modifying
    @Query(value = "update reply r set r.contents=:contents where r.id=:id", nativeQuery = true)
    void updateReply(@Param("id") Long id, @Param("contents") String contents);


}
