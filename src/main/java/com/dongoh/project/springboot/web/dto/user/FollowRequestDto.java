package com.dongoh.project.springboot.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FollowRequestDto {
    Long sendUserId;
    Long receiveUserId;

    @Builder
    FollowRequestDto(Long sendUserId, Long receiveUserId)
    {
        this.sendUserId=sendUserId;
        this.receiveUserId=receiveUserId;
    }

}
