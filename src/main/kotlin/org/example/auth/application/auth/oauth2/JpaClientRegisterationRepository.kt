package org.example.auth.application.auth.oauth2

import org.example.auth.domain.auth.dao.OAuth2ClientRepository
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.stereotype.Service

@Service
class JpaClientRegisterationRepository(
    private val oAuth2ClientRepository: OAuth2ClientRepository,
) : ClientRegistrationRepository, Iterable<ClientRegistration> {
    override fun findByRegistrationId(registrationId: String): ClientRegistration? {
        println("================")
        println(registrationId)
        println("================")

        val clientOptional = oAuth2ClientRepository.findByRegistrationId(registrationId)

        if (clientOptional.isEmpty) return null
        val client = clientOptional.get()

        return runCatching {
            CommonOAuth2Provider.valueOf(registrationId.uppercase())
        }.mapCatching { provider ->
            provider.getBuilder(registrationId)
                .clientId(client.clientId)
                .clientSecret(client.clientSecret)
                .build()
        }.getOrElse {
            // 실패 시 직접 ClientRegistration을 빌드
            val builder =
                ClientRegistration.withRegistrationId(registrationId)
                    .clientId(client.clientId)
                    .clientSecret(client.clientSecret)

            client.authorizationGrantType?.let { builder.authorizationGrantType(AuthorizationGrantType(it)) }
            client.redirectUri?.let { builder.redirectUri(it) }
            client.authorizationUri?.let { builder.authorizationUri(it) }
            client.tokenUri?.let { builder.tokenUri(it) }
            client.userInfoUri?.let { builder.userInfoUri(it) }
            client.userNameAttributeName?.let { builder.userNameAttributeName(it) }
            client.clientName?.let { builder.clientName(it) }

            client.clientAuthenticationMethod?.let { builder.clientAuthenticationMethod(ClientAuthenticationMethod(it)) }

            client.jwkSetUri?.let { builder.jwkSetUri(it) }
            client.issuerUri?.let { builder.issuerUri(it) }

            builder.build()
        }
    }

    override fun iterator(): Iterator<ClientRegistration> {
        return oAuth2ClientRepository.findAll().mapNotNull { findByRegistrationId(it.registrationId!!) }.iterator()
    }
}
