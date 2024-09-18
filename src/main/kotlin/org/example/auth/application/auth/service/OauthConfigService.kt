package org.example.auth.application.auth.service

import org.example.auth.domain.auth.entity.auth.OAuth2Client

interface OauthConfigService {
    fun registeOauthProvider(client: OAuth2Client)

    fun listOauthProvider(): List<OAuth2Client>

    fun delOauthProvider(id: Long)
}
