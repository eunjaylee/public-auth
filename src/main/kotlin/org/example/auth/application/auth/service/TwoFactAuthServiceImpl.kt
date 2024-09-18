package org.example.auth.application.auth.service

import org.example.auth.application.auth.service.UserServiceImpl.Companion.logger
import org.example.auth.domain.auth.dao.UserPersonalInfoDao
import org.example.auth.domain.auth.entity.enumtype.CommonCode
// import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class TwoFactAuthServiceImpl(
    private val userPersonalInfoDao: UserPersonalInfoDao,
    private val redisTemplate: RedisTemplate<String, Any>,
//    private val streamBridge: StreamBridge,
) : TwoFactAuthService {
    /**
     * 이메일 밸리데이션 검증  - 2FA인증 관련 1차
     */
    override fun checkVerifyEmail(to: String): String {
        // 중복체크도 하자
        userPersonalInfoDao.findByEmail(to).ifPresent {
            throw Exception(CommonCode.EMAIL_ALREADY_EXISTS.message)
        }

        var verifyCode: String = ""

        for (x in 0..3)
            verifyCode += (Math.random() * 10).toInt()

        val vop = redisTemplate.opsForValue()
        vop.set(verifyCode, to, 3, TimeUnit.MINUTES)

        logger.debug("code/email :  {}, {} ", verifyCode, to)

        // TODO send email...

        return verifyCode
    }

    /**
     * 인증번호 체크 - 2FA 인증 2차
     */
    override fun checkVerifyCode(verifyCode: String): String? {
        // 해당

        val vop = redisTemplate.opsForValue()
        val emailCode = vop.get(verifyCode).toString()
        logger.debug("verifyCode : {} - {}", verifyCode, emailCode)
        return emailCode
    }

    override fun sendLink() {
        TODO("Not yet implemented")
    }

    override fun verifyLink() {
        TODO("Not yet implemented")
    }
}
