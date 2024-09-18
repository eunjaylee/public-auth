package org.example.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository

@Configuration
class Oauth2ClientConfig {
    // 해당 bean이 등록되면 InMemoryOAuth2AuthorizedClientService를 JdbcOAuth2AuthorizedClientService로 변경해주는 역할
    @Bean
    fun authorizedClientService(
        jdbcOperations: JdbcOperations,
        clientRegistrationRepository: ClientRegistrationRepository,
    ): OAuth2AuthorizedClientService { // google, facebook, kakao 등의 client 정보를 가지고 있다.
        return JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository)
    }
}
