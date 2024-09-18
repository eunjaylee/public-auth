package org.example.auth.adaptor.auth.controller.admin

import io.swagger.v3.oas.annotations.Operation
import mu.KLogging
import org.example.auth.adaptor.dto.BooleanResponse
import org.example.auth.application.auth.service.role.AuthorityService
import org.example.auth.domain.auth.Account
import org.example.auth.domain.auth.entity.UserDetail
import org.example.auth.domain.auth.entity.auth.Authorities
import org.example.auth.infrastructure.util.modelMapTo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/api/v1/user/role")
class RoleController(
    private val authorityService: AuthorityService,
) {
    companion object : KLogging()

    /**
     * 권한 목록을 가져온다.
     */
    @Operation(summary = "권한 목록 확인")
    @GetMapping("/authorityList")
    fun getAuthorityList(): List<Authorities> {
        return authorityService.getAuthorityList()
    }

//    /**
//     * 권한 계층정보를 가져온다.
//     */
//    fun getHierarchy() : List<> {
//        authorityService.
//    }

    /**
     * 회원별 권한이름으로 권한 부여
     */
    @Operation(summary = "권한 부여")
    @PostMapping("/authority")
    fun addUserAuthority(
        @AuthenticationPrincipal account: Account,
        authoritiName: String,
    ): UserDetail {
        return authorityService.addUserAuthority(account.modelMapTo(), authoritiName)
    }

    /**
     * 회원별 권한 삭제
     */
    @Operation(summary = "권한 삭제")
    @DeleteMapping("/authority")
    fun delUserAuthority(userAuthoritiesSeq: Long): BooleanResponse {
        authorityService.delUserAuthority(userAuthoritiesSeq)
        return BooleanResponse("del authority", true)
    }
}
