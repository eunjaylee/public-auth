package org.example.auth.application.auth.service

import org.example.auth.domain.auth.Account
import org.example.auth.domain.auth.dao.UserDao
import org.example.auth.domain.auth.dao.UserPersonalInfoDao
import org.example.auth.domain.auth.entity.enumtype.CommonCode
import org.example.auth.domain.auth.entity.enumtype.UserStatus
import org.example.auth.infrastructure.external.DropUser
import org.springframework.stereotype.Service

@Service
class DropUserServiceImpl(
    private val userDao: UserDao,
    private val userPersonalInfoDao: UserPersonalInfoDao,
    private val dropUser: DropUser,
) : DropUserService {
    /**
     * 탈퇴 설문
     */
    // TODO 탈퇴 설문 문항 -> 추후 Config로 설문문항을 이동해야 할 수 있음
    override fun quizList(): List<String> =
        listOf(
            "낮은 수익률",
            "매력없는 투자상품",
            "서비스 불편",
            "복잡한 서비스 구조",
            "자산관리 신뢰도",
            "개인정보 침해우려",
        )

    override fun askDropOut(userDetail: Account): Boolean {
        val user =
            userDao.findById(userDetail.userSeq)
                .orElseThrow { Exception(CommonCode.USER_NOT_FOUND.message) }

        // TODO 회원이 탈퇴 가능한 상태인지 확인한다.
        // 1. 포인트가 남아있는가?
        // 2. 기타 등등..

        user.userStatus = UserStatus.DROPOUT
        userDao.save(user)
        return true
    }

    /**
     * 탈퇴 취소
     */
    override fun cancelDropOut(): Boolean {
        userDao.findAll().forEach {
            it.userStatus = UserStatus.NORMAL
            userDao.save(it)
        }
        return true
    }

    /**
     * 탈퇴 실행
     */
    override fun doDropOut(user: Account): Boolean {
        val userPersonalInfo = userPersonalInfoDao.findById(user.userSeq).orElseThrow { Exception("탈퇴 가능한 유져가 없습니다.") }
        dropUser.save(userPersonalInfo)

        userDao.deleteById(user.userSeq)
        userPersonalInfoDao.deleteById(user.userSeq)
        return true
    }
}
