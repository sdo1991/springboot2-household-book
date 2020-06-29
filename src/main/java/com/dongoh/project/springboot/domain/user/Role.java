package com.dongoh.project.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    BLOCK("ROLE_BLOCK","차단"),
    GUEST("ROLE_GUEST","손님"),
    USER("ROLE_USER","사용자"),
    ADMIN("ROLE_ADMIN","관리자");
    private final String key;
    private final String title;

}
