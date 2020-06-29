package com.dongoh.project.springboot.web.dto.message;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class AlarmResponseDto {
    List<MessageResponseDto> messages;
    Integer alarmCount;
    boolean activated;

    @Builder
    AlarmResponseDto(List messages)
    {
        this.messages=messages;
        this.alarmCount=messages.size();
        if(this.alarmCount==0)
            this.activated=false;
        else
            this.activated=true;

    }
}
