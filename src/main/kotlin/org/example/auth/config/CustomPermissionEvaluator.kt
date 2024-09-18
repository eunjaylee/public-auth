package org.example.auth.config

import org.example.auth.adaptor.cms.dto.PostsParamReq
import org.example.auth.domain.auth.Account
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CustomPermissionEvaluator : PermissionEvaluator {
    override fun hasPermission(
        authentication: Authentication?,
        targetDomainObject: Any?,
        permission: Any?,
    ): Boolean {
        // 권한객체가 없으면 항상 실패 --> guest도 null인가?
        if (authentication == null || !authentication.isAuthenticated) {
            return false
        }

        // admin이면 항상 true 인데 isOwner이면 false일 수 있다
        if (authentication.authorities.contains(SimpleGrantedAuthority("ROLE_ADMIN")) && permission.toString() != "isOwner") {
            return true
        }

        if (targetDomainObject is PostsParamReq) {
            return checkBoardPermission(authentication as Account, targetDomainObject, permission.toString())
        }

        return true
    }

    // TODO
    private val publicBoard: List<String> = arrayListOf("NOTICE", "FAQ", "EVENT")

    private fun checkBoardPermission(
        account: Account,
        postsDto: PostsParamReq,
        permission: String,
    ): Boolean {
        val roleName = "ROLE_${postsDto.boardType}" // 게시판 권한

        // 해당 동작이 읽기라면 public 게시판에 읽기는 가능하다.
        if (permission == "canRead" && publicBoard.contains(postsDto.boardType)) {
            return true
        }

        // TODO : 고민중 1:1 게시판에는 따로 권한을 안붙여도 될듯... 메소드 쿼리 분리?

        // 게시판권한도 있고 내가 작성했다면
        if (account.authorities.contains(SimpleGrantedAuthority(roleName))) {
            if (permission != "isOwner") {
                return true
            }
            if (account.userSeq == postsDto.userSeq) {
                return true
            }
            // 구매 내역에 있으면 true...
        }

        return false
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any?,
    ): Boolean {
        // 이 메소드는 사용하지 않으므로, false를 반환합니다.
        return false
    }
}
