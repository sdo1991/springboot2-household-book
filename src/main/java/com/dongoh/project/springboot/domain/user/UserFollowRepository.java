package com.dongoh.project.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    Integer countByUserAndFollower(User user, User follower);

    List<UserFollow> findByUser(User user);
}
