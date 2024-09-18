package org.example.auth.domain.auth.entity.enumtype

enum class UserStatus(val desc: String) {
    GUEST("손님"),
    NORMAL("정상회원"),
    ABNORMAL("비정상회원"),
    ASK("탈퇴요청"),
    DROPOUT("탈퇴"),
}
