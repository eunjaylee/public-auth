package org.example.auth.infrastructure.notify

enum class NotiServiceType(val desc: String) {
    EMAIL("이메일"),
    SMS("문자"),
    ALIMTALK("알림톡"),
    KAKAOTALK("카카오톡"),
    FCM("구글 메시징 플랫폼"),
}
