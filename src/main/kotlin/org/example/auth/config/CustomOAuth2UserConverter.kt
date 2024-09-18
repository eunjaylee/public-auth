package org.example.auth.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import java.util.*

class CustomOAuth2UserConverter : Converter<Map<String, Object>, OAuth2User> {
    override fun convert(attributes: Map<String, Object>): OAuth2User {
        val id = attributes.get("id")
        val name = attributes.get("name")
        val email = attributes.get("email")

        // 사용자 권한 설정 (예제에서는 기본 권한 부여)
        val authorities: Set<OAuth2UserAuthority> = Collections.singleton(OAuth2UserAuthority(attributes))

        // DefaultOAuth2User를 사용하여 OAuth2User 객체를 반환
        return DefaultOAuth2User(authorities, attributes, "name")
    }
}
