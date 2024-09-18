package org.example.auth.application.auth.oauth2

object OAuth2UserInfoFactory {
    fun getOAuth2UserInfo(
        registrationId: String,
        attributes: Map<String, Any>,
    ): OAuth2UserInfo {
        return when (registrationId) {
            AuthProvider.google.name -> GoogleOAuth2UserInfo(registrationId, attributes)
            AuthProvider.facebook.name -> FacebookOAuth2UserInfo(registrationId, attributes)
            AuthProvider.kakao.name -> KakaoUserInfo(registrationId, attributes)
            else -> throw Exception("OAUTH2_PROVIDER_NOT_SUPPORTED")
        }
    }
}

abstract class OAuth2UserInfo(private val registrationId: String, var attributes: Map<String, Any>) {
    fun getProviderId(): String = registrationId

    abstract fun getId(): String

    abstract fun getName(): String

    abstract fun getEmail(): String

    abstract fun getImageUrl(): String?
}

class KakaoUserInfo(registrationId: String, attributes: Map<String, Any>) : OAuth2UserInfo(registrationId, attributes) {
    override fun getId() = (attributes["id"] as Long).toString()

    override fun getName() = (attributes["properties"] as Map<String, Any?>)["nickname"] as String

    override fun getEmail() = (attributes["kakao_account"]as Map<String, Any?>)["email"] as String

    override fun getImageUrl() = ""
}

class GoogleOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>) : OAuth2UserInfo(registrationId, attributes) {
    override fun getId(): String = attributes["sub"] as String

    override fun getName(): String = attributes["name"] as String

    override fun getEmail(): String = attributes["email"] as String

    override fun getImageUrl(): String = attributes["picture"] as String
}

class FacebookOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>) : OAuth2UserInfo(registrationId, attributes) {
    override fun getId(): String = attributes["id"] as String

    override fun getName(): String = attributes["name"] as String

    override fun getEmail(): String = attributes["email"] as String

    override fun getImageUrl(): String? {
        if (attributes.containsKey("picture")) {
            val pictureObj = attributes["picture"] as Map<String, Any?>?
            if (pictureObj!!.containsKey("data")) {
                val dataObj = pictureObj["data"] as Map<String, Any?>?
                if (dataObj!!.containsKey("url")) {
                    return dataObj["url"] as String
                }
            }
        }
        return null
    }
}
