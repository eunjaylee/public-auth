package org.example.auth.application.auth.oauth2

enum class AuthProvider(val desc: String) {
    local("로컬"),
    google("구글"),
    facebook("페이스북"),
    kakao("카카오"),
}
