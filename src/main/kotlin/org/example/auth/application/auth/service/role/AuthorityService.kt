package org.example.auth.application.auth.service.role

import org.example.auth.domain.auth.entity.UserDetail
import org.example.auth.domain.auth.entity.auth.Authorities

interface AuthorityService {
    /**
     * 회원 권한 리스트 - distinct를 하거나 별도 테이블 생성을 하거나?
     */
    fun getAuthorityList(): List<Authorities>

    /**
     * 권한 이름 삭제 - 해당 권한에 유져 명수 확인
     */
    fun delAuthority(authority: String)

    /**
     * 회원별 권한이름으로 권한 부여
     */
    fun addUserAuthority(
        userDetail: UserDetail,
        authority: String,
    ): UserDetail

    /**
     * 권한 유져 삭제 - 해당 권한에 유져 명수 확인
     */
    fun removeUserAuthority(
        userDetail: UserDetail,
        authority: String,
    )

    /**
     * 회원별 권한 삭제
     */
    fun delUserAuthority(userAuthoritiesSeq: Long)
}
