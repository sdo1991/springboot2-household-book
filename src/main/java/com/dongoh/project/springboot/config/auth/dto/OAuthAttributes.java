package com.dongoh.project.springboot.config.auth.dto;

import com.dongoh.project.springboot.domain.user.Role;
import com.dongoh.project.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String ,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public OAuthAttributes(Map<String ,Object> attributes, String nameAttributeKey, String name, String email, String picture)
    {
        this.attributes=attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role= Role.GUEST;
    }

    public static com.dongoh.project.springboot.config.auth.dto.OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String ,Object> attributes)
    {
        if("naver".equals(registrationId))
        {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static com.dongoh.project.springboot.config.auth.dto.OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes)
    {
        return com.dongoh.project.springboot.config.auth.dto.OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static com.dongoh.project.springboot.config.auth.dto.OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes)
    {
        Map<String, Object> response=(Map<String, Object>)attributes.get("response");

        return com.dongoh.project.springboot.config.auth.dto.OAuthAttributes.builder()
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .picture((String)response.get("picture"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity()
    {
        return User.builder().name(name).email(email).picture(picture).role(role).build();
    }
}
