package org.example.auth.application.auth.oauth2

import mu.KLogging
import org.example.auth.domain.auth.Account
import org.example.auth.domain.auth.dao.UserDao
import org.example.auth.domain.auth.dao.UserProviderJpaDao
import org.example.auth.domain.auth.entity.auth.UserAuthorities
import org.example.auth.domain.auth.entity.auth.UserProvider
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userDao: UserDao,
    private val userProviderDao: UserProviderJpaDao,
) : DefaultOAuth2UserService() {
    companion object : KLogging()

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        logger.debug("CustomOAuth2UserService loadUser")
        val oAuth2User = super.loadUser(oAuth2UserRequest)

        logger.debug("registrationId : {}", oAuth2UserRequest.clientRegistration.registrationId)

        logger.debug("attributes : {}", oAuth2User.attributes)

        val oAuth2UserInfo: OAuth2UserInfo =
            OAuth2UserInfoFactory.getOAuth2UserInfo(
                oAuth2UserRequest.clientRegistration.registrationId,
                oAuth2User.attributes,
            )

        val userProvider = userProviderDao.findById(oAuth2UserInfo.getId())

        if (userProvider.isPresent && userProvider.get().userSeq > 0) {
            val userDetail = userDao.findById(userProvider.get().userSeq).orElseThrow { OAuth2AuthenticationException("존재하지 않는 회원임") }

            return Account(userDetail)
        } else if (userProvider.isEmpty) {
            userProviderDao.save(
                UserProvider(
                    id = oAuth2UserInfo.getId(),
                    providerId = oAuth2UserInfo.getProviderId(),
                    userName = oAuth2UserInfo.getName(),
                ),
            )
        }

        return Account(userId = oAuth2UserInfo.getId(), userName = oAuth2UserInfo.getName()).apply {
            providerId = oAuth2UserInfo.getProviderId()
            userAuthorities = mutableSetOf(UserAuthorities(authority = "ROLE_OAUTH"))
        }
    }
}
