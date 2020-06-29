package com.dongoh.project.springboot.config.auth;

import com.dongoh.project.springboot.config.auth.dto.OAuthAttributes;
import com.dongoh.project.springboot.config.auth.dto.SessionUser;
import com.dongoh.project.springboot.domain.user.User;
import com.dongoh.project.springboot.domain.user.UserRepository;
import com.dongoh.project.springboot.web.dto.user.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate=new DefaultOAuth2UserService();
        OAuth2User oAuth2User=delegate.loadUser(userRequest);

        String registrationId=userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName=userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes=OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user=saveOrUpdate(attributes);

        //System.out.println("role:" +user.getRoleKey());
        //httpSession.setAttribute("user",new SessionUser(user));
        SimpleGrantedAuthority sga= new SimpleGrantedAuthority(user.getRole().getKey());
        return new DefaultOAuth2User(
                Collections.singleton(sga),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes)
    {
        User user=userRepository.findByEmail(attributes.getEmail())
                .map(entity->entity.updateBySocial(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());


        return userRepository.save(user);
    }
}
