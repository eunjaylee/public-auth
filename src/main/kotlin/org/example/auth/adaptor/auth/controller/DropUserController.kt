package org.example.auth.adaptor.auth.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.auth.adaptor.auth.controller.UserController.Companion.logger
import org.example.auth.adaptor.dto.MessageResponse
import org.example.auth.application.auth.service.DropUserService
import org.example.auth.domain.auth.Account
import org.example.auth.infrastructure.notify.NotifyServiceFactory
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/drop-user")
class DropUserController(
    private val dropUserService: DropUserService,
    private val messageSource: MessageSource,
    val notifyServiceFactory: NotifyServiceFactory,
) {
    /**
     * 회원 탈퇴 문항
     */
    @Operation(summary = "회원탈퇴 문제 요청")
    @RequestMapping("/quiz")
    fun quizWithdrawal(): Map<String, List<String>> {
        return mapOf("message" to listOf("탈퇴 신청 문항"), "data" to dropUserService.quizList())
    }

    /**
     * 회원 탈퇴 가능여부 검사
     */
    @Operation(summary = "회원탈퇴 가능여부 검사")
    @RequestMapping("/check")
    fun checkWithdrawal(): Map<String, List<String>> {
        // TODO factory로 만들고 merge를 하도록 하면 깔끔하지 않을까?

        TODO("Not yet implemented")
    }

    /**
     * 회원 탈퇴 요청
     */
    @Operation(summary = "회원탈퇴 신청")
    @PostMapping("/ask")
    fun askWithdrawal(
        @AuthenticationPrincipal user: Account,
        dropAnswer: String,
    ): MessageResponse {
        // TODO 탈퇴시 유저의 응답내용을 저장 후 분석하는 절차가 필요할듯
        // TODO 그 외에도 유저탈퇴시 해줘야 하는 작업들이 있는데 기획협의가 필요
        logger.debug("answer {} : {}", user.userSeq, dropAnswer)

        // 탈퇴 가능여부 체크 할것들이 있음
        dropUserService.askDropOut(user)

        return MessageResponse(message = messageSource.getMessage("ask_drop_out", null, LocaleContextHolder.getLocale()))
    }

    /**
     * 실제 회원 탈퇴
     */
    @Operation(summary = "회원탈퇴")
    @PostMapping("/out")
    fun doWithdrawal(
        @AuthenticationPrincipal user: Account,
    ): MessageResponse {
        // 탈퇴 가능여부 체크 할것들이 있음
        dropUserService.doDropOut(user)
        // 탈퇴이후 로그아웃으로
        return MessageResponse(message = messageSource.getMessage("do_drop_out", null, LocaleContextHolder.getLocale()))
    }

    /**
     * 회원 탈퇴 취소
     */
    @Operation(summary = "회원탈퇴 신청 취소 ")
    @GetMapping("/cancel")
    @Deprecated("탈퇴 취소는 현재 고려 안함")
    fun cancelDropOut(): MessageResponse {
        dropUserService.cancelDropOut()
        return MessageResponse(message = messageSource.getMessage("cancel_drop_out", null, LocaleContextHolder.getLocale()))
    }
}
