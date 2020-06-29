package com.dongoh.project.springboot.domain.message;

import com.dongoh.project.springboot.domain.post.Post;
import com.dongoh.project.springboot.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    void deleteByPost(Post post);

    List<Message> findByReceiverAndTypeNot(User receiver,String type, Pageable pageable);

    List<Message> findByReceiverAndIsCheckedAndTypeNot(User receiver, boolean isChecked, String type);

    List<Message> findByType(String type, Pageable pageable);

    Integer countByReceiver(User receiver);
    Integer countByType(String type);

    @Modifying
    @Query(value = "UPDATE Message m set m.is_checked=true where m.receiver=:receiver", nativeQuery = true)
    void updateIsCheckedByUserId(@Param("receiver") Long receiver);
}
