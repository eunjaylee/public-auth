package org.example.auth.adaptor.auth.controller.admin

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import mu.KLogging
import org.example.auth.adaptor.auth.dto.AccountDto
import org.example.auth.adaptor.auth.dto.UserLoginHistoryDto
import org.example.auth.adaptor.auth.dto.UserSearchParamReq
import org.example.auth.adaptor.dto.BooleanResponse
import org.example.auth.application.auth.service.UserService
import org.example.auth.application.auth.service.role.AuthorityService
import org.example.auth.infrastructure.util.modelMapTo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/api/v1/user")
class AdminUserController(
    private val userService: UserService,
    private val authorityService: AuthorityService,
) {
    companion object : KLogging()

    /**
     * 고객 목록을 가져온다.
     */
    @Operation(summary = "회원 목록 검색")
    @GetMapping("/userList")
    fun getUserList(
        @RequestBody param: UserSearchParamReq,
        @PageableDefault(page = 0, size = 20) page: Pageable,
    ): Page<AccountDto> {
        return userService.userSearch(param, page).map { it.modelMapTo() }
    }

    /**
     * 고객 상세 정보를 가져온다.
     */
    @Operation(summary = "회원 정보 확인")
    @GetMapping("/info")
    fun getUserInfo(
        @RequestParam userSeq: Long,
    ): AccountDto {
        val userInfo = userService.getUser(userSeq) ?: throw Exception("user not found")
        return userInfo.modelMapTo()
    }

    /**
     * 고객 정보를 수정한다.
     */
    @Operation(summary = "회원 정보 수정")
    @PostMapping("/info")
    fun updateUserInfo(
        @Valid @RequestBody account: AccountDto,
    ): BooleanResponse {
        return BooleanResponse("modify userInfo", userService.modifyUserInfo(account.modelMapTo()))
    }

    /**
     * 고객 권한정보를 추가한다.
     */
    @Operation(summary = "회원 권한 변경")
    @PostMapping("/role")
    fun updateUserRole(
        account: AccountDto,
        authority: String,
    ): BooleanResponse {
        authorityService.addUserAuthority(account.modelMapTo(), authority)
        return BooleanResponse("add user role", true)
    }

    /**
     * 고객 권한정보를 삭제한다.
     */
    @Operation(summary = "회원 권한 변경")
    @DeleteMapping("/role")
    fun deleteUserRole(
        account: AccountDto,
        authority: String,
    ): BooleanResponse {
        authorityService.removeUserAuthority(account.modelMapTo(), authority)
        return BooleanResponse("del user role", true)
    }

    /**
     * 고객 상세 정보를 가져온다.
     */
    @Operation(summary = "회원 로그인 이력 조회")
    @GetMapping("/loginHistory")
    fun getLoginHistory(
        userSeq: Long,
        @PageableDefault(page = 0, size = 20) page: Pageable,
    ): Slice<UserLoginHistoryDto> {
        return userService.getLoginHistory(userSeq, page).map { it.modelMapTo() }
    }
}
