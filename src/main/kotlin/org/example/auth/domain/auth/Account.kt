package org.example.auth.domain.auth

import org.example.auth.domain.auth.entity.UserDetail
import org.example.auth.domain.auth.entity.auth.UserAuthorities
import org.example.auth.domain.auth.entity.enumtype.UserStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.time.LocalDateTime

class Account(
    /** 로그인 ID */
    val userId: String = "",
    /** 회원이름 */
    val userName: String = "",
    /** 로그인 PWD */
    var userPwd: String = "",
) : UserDetails, OAuth2User {
    constructor(userDetail: UserDetail) : this(
        userId = userDetail.userId,
        userName = userDetail.userName,
    ) {
        /** 회원 고유 키값 */
        this.userSeq = userDetail.userSeq
        /** 회원 권한 목록 */
        this.userAuthorities = userDetail.userAuthorities
        /** 회원 상태 */
        this.userStatus = userDetail.userStatus
    }

    /** 회원 고유 키값 */
    var userSeq: Long = 0

    /** 3자 인증 정보 */
    var providerId: String? = null

    /** 프로파일 이미지 */
    var imageUrl: String? = null

    /** 이메일 인증여부 */
    var emailVerified: Boolean? = true

    /** 게정 잠김 여부 */
    var accountLocked: Boolean = false

    /** 마지막 로그인 일시 */
    var lastLoginDtime: LocalDateTime? = null

    /** 회원 상태 */
    var userStatus: UserStatus = UserStatus.NORMAL

    var expiredAt: LocalDateTime? = null

    /** 수정일시 */
    var updateAt: LocalDateTime = LocalDateTime.now()

    /** 가입일시 */
    var createAt: LocalDateTime = LocalDateTime.now()

    /** oauth2 속성들 **/
    private var attributes: Map<String, Any>? = null

    override fun getAttributes() = attributes

    /** 회원 권한 목록 */
    var userAuthorities: MutableSet<UserAuthorities> = mutableSetOf()

    override fun getName(): String = userName

    override fun getAuthorities(): List<GrantedAuthority> {
        return this.userAuthorities.map { SimpleGrantedAuthority(it.authority) }
    }

    override fun getPassword(): String = userPwd

    override fun getUsername(): String = userId

    override fun isAccountNonExpired(): Boolean {
        if (expiredAt == null) return true
        return LocalDateTime.now() < expiredAt
    }

    override fun isAccountNonLocked(): Boolean = !accountLocked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean {
        return UserStatus.NORMAL == userStatus
    }

//    private var verifyCodeChecked : Boolean = false
//
//    fun isValidVerifyCode(code : String = "") : Boolean {
//        if(code == "") return verifyCodeChecked
//        verifyCodeChecked = (code == verifyCode)
//        return verifyCodeChecked
//    }

    companion object {
        fun makeVerifyCode(len: Int = 4): String {
            val verifyCode = StringBuilder(len)
            val random = java.util.Random()

            for (i in 0 until len) {
                val digit = random.nextInt(10) // 0부터 9까지의 숫자를 생성
                verifyCode.append(digit)
            }

            return verifyCode.toString()
        }
    }
}
