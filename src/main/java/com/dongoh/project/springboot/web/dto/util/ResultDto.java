package com.dongoh.project.springboot.web.dto.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@Getter
public class ResultDto {
    private Boolean notError;
    private String message;
    private HashMap<String, String> params;

    @Builder
    public ResultDto(Boolean notError, String Message) {
        this.notError = notError;
        this.message = Message;
        this.params = new HashMap<String, String>();
    }

    public ResultDto setState(Boolean notError, String message) {
        this.notError = notError;
        this.message = message;

        return this;
    }

    public void setMessage(String message)
    {
        this.message=message;
    }

    public void addParam(String key, String value)
    {
        params.put(key, value);
    }

}
