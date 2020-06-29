package com.dongoh.project.springboot.domain.post;

import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.web.dto.post.PostUpdateRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT P.* FROM POST P, USER U WHERE p.author=u.id and (p.author in :followers or u.id=:loginUserId)", nativeQuery = true)//SpringDataJpa에서 지원하지 않는 메소드를 작성하기 위해 사용함
    List<Post> findPostForFollowers(@Param("loginUserId") Long loginUserId, @Param("followers") Long[] followers,Pageable pageable);

    @Query(value = "SELECT P.* FROM POST P, USER U WHERE p.author=u.id and ((U.main_lang IN :interestLang and u.interest_lang like %:loginUserLang%) or u.id=:loginUserId)", nativeQuery = true)//SpringDataJpa에서 지원하지 않는 메소드를 작성하기 위해 사용함
    List<Post> findAllPost(@Param("interestLang") String[] interestLang, @Param("loginUserLang") String loginUserLang, @Param("loginUserId") Long loginUserId, Pageable pageable);

    @Query(value = "SELECT P.* FROM POST P, USER U WHERE p.author=u.id and p.tags like %:tags% and ((U.main_lang IN :interestLang and u.interest_lang like %:loginUserLang%) or u.id=:loginUserId)", nativeQuery = true)//SpringDataJpa에서 지원하지 않는 메소드를 작성하기 위해 사용함
    List<Post> findPostByTags(@Param("tags") String tags, @Param("interestLang") String[] interestLang, @Param("loginUserLang") String loginUserLang, @Param("loginUserId") Long loginUserId, Pageable pageable);

    List<Post> findByAuthor(User author, Pageable pageable);


    @Modifying
    @Query(value = "UPDATE POST p set p.contents=:contents, p.tags=:tags where p.id=:id", nativeQuery = true)
    void updatePost(@Param("contents") String contents, @Param("tags") String tags, @Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE POST p set p.likes=p.likes+1 where p.id=:id", nativeQuery = true)
    void addLike(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE POST p set p.replies=p.replies+1 where p.id=:id", nativeQuery = true)
    void addReplies(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE POST p set p.replies=p.replies-1 where p.id=:id", nativeQuery = true)
    void deleteReplies(@Param("id") Long id);

    @Query(value = "SELECT replies FROM post p WHERE p.id=:id", nativeQuery = true)
    Integer findRepliesNumberById(@Param("id") Long id);

}
