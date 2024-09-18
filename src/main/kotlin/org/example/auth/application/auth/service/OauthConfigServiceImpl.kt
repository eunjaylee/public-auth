package org.example.auth.application.auth.service

import org.example.auth.domain.auth.dao.OAuth2ClientRepository
import org.example.auth.domain.auth.entity.auth.OAuth2Client
import org.springframework.stereotype.Service

@Service
class OauthConfigServiceImpl(
    private val oAuth2ClientRepository: OAuth2ClientRepository,
) : OauthConfigService {
    override fun registeOauthProvider(client: OAuth2Client) {
        oAuth2ClientRepository.save(client)
    }

    override fun listOauthProvider(): List<OAuth2Client> {
        return oAuth2ClientRepository.findAll()
    }

    override fun delOauthProvider(id: Long) {
        oAuth2ClientRepository.deleteById(id)
    }
}
