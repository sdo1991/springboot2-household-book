package com.dongoh.project.springboot.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserFollow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "follower", nullable = false)
    private User follower;

    @Builder
    UserFollow(User user, User follower)
    {
        this.user=user;
        this.follower=follower;
    }
}
