package org.example.auth.application.auth.service

import org.example.auth.domain.auth.Account

interface DropUserService {
    /**
     * 회원 탈퇴 문제
     */
    fun quizList(): List<String>

    /**
     * 회원 탈퇴 요청
     */
    fun askDropOut(userDetail: Account): Boolean

    /**
     * 회원 탈퇴 취소
     */
    fun cancelDropOut(): Boolean

    /**
     * 회원 탈퇴 처리
     */
    fun doDropOut(user: Account): Boolean
}
