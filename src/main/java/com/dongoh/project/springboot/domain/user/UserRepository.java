package com.dongoh.project.springboot.domain.user;

import com.dongoh.project.springboot.domain.reply.Reply;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
/*
    @Query(value = "select * from reply r where r.post = :post", nativeQuery = true)
    List<Reply> findUserById(@Param("id") Long id);
*/
    Optional<User> findByEmail(String email);

    User findByEmailAndRole(String email, Role role);

    Integer countByEmail(String email);
}
