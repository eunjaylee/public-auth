package org.example.auth.application.auth.service

import jakarta.servlet.http.HttpSession
import mu.KLogging
import org.example.auth.adaptor.auth.dto.NiceRequest
import org.example.auth.config.NiceCheck
import org.example.auth.domain.auth.dao.UserPersonalInfoDao
import org.example.auth.domain.auth.entity.UserPersonalInfo
import org.example.auth.domain.auth.entity.enumtype.CommonCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

@Service
class IdentificationNiceServiceImpl(
    val niceCheck: NiceCheck,
    val userPersonalInfoDao: UserPersonalInfoDao,
//    val redisTemplate : RedisTemplate<String, Any>
) : IdentificationService {
    companion object : KLogging()

    @Value("\${nice.adult-age}")
    lateinit var adultAge: String

    @Value("\${nice.code}")
    lateinit var siteCode: String

    @Value("\${nice.password}")
    lateinit var sitePassword: String

    @Value("\${nice.domain}")
    lateinit var domain: String // nice에서 응답을 보내줄 도메인

    override fun encCipherData(
        session: HttpSession,
        reqNum: String?,
    ): String {
        // reqNum이 null이면 niceCheck에서 새로운 아이디를 가져온다.
        var requestNumber = reqNum ?: niceCheck.getRequestNO(siteCode)

        session.setAttribute("REQ_SEQ", requestNumber)
        logger.debug("http://localhost:8080/identity/check")
        logger.debug("url : {}{} ", domain, "/identity/check")

        var niceRequest =
            NiceRequest(
                sSiteCode = siteCode,
                sSitePassword = sitePassword,
                sRequestNumber = requestNumber,
                sReturnUrl = domain + "/identity/check",
                sErrorUrl = domain + "/identity/check",
            )

        val iReturn = niceCheck.fnEncode(siteCode, sitePassword, niceRequest.getPlainData())

        if (iReturn == 0) {
            return niceCheck.cipherData
        } else {
            var message =
                when (iReturn) {
                    -1 -> "암호화 시스템 에러입니다."
                    -2 -> "암호화 처리오류입니다."
                    -3 -> "암호화 데이터 오류입니다."
                    -9 -> "입력 데이터 오류입니다."
                    else -> "알수 없는 에러 입니다. iReturn : " + iReturn
                }
            throw Exception(message)
        }
    }

    @Throws(Exception::class)
    override fun decEncodeData(
        encodeData: String,
        session: HttpSession,
    ): Map<String, String> {
        val userPersonalInfo = UserPersonalInfo()
        logger.debug("encodeData : {} ", encodeData)
        val iReturn: Int = niceCheck.fnDecode(siteCode, sitePassword, encodeData)
        logger.debug("iReturn : {} ", iReturn)

        var message = "인증 성공"
        if (iReturn == 0) {
            var plainData = niceCheck.plainData

            var resultMap = niceCheck.fnParse(plainData) as HashMap<String, String>

//            if (resultMap.get("REQ_SEQ") == reqSeq) {
            // 세션에 저장하고 끝낸다. --> 본인 인증상태..
            logger.debug("resultMap : {} ", resultMap)

            if (getAmericanAge(resultMap.get("BIRTHDATE")) < adultAge.toInt()) {
                throw Exception(CommonCode.USER_NOT_ADULT.toString())
            }

            if (resultMap.get("NATIONAINFO") == "1") {
                throw Exception(CommonCode.USER_NOT_NATIVE.toString())
            }

            // 무조건 세션에 현재 본인인증 내역을 저장해야 한다.
            var newPersonalInfo = parseMap(resultMap)

            // 찾아진 CI가 있으면 일부 항목을 new에 채우자
            userPersonalInfoDao.findByCi(resultMap.get("CI") as String).ifPresent {
//                newPersonalInfo["userSeq"] = it.userSeq
//                newPersonalInfo.userDetail = it.userDetail
            }

//            var userId: String
//            if (newPersonalInfo.userDetail != null)
//                userId = newPersonalInfo.userDetail!!.userId
//            else {
//                userId = UUID.randomUUID().toString()
//                newPersonalInfo.userDetail = UserDetail(userId = userId)
//            }

//            // TODO Redis 직접 핸들링 하는 부분은 함수로 빼야함
//            val vop = redisTemplate.opsForValue()
//            vop.set(userId, newPersonalInfo, 3, TimeUnit.MINUTES)

            session.setAttribute("certify", newPersonalInfo)
            return newPersonalInfo
        } else {
            message =
                when (iReturn) {
                    -1 -> "암호화 시스템 에러입니다."
                    -2 -> "암호화 처리오류입니다."
                    -3 -> "암호화 데이터 오류입니다."
                    -9 -> "입력 데이터 오류입니다."
                    else -> "알수 없는 에러 입니다. iReturn : " + iReturn
                }
        }
        throw Exception(message)
    }

    fun parseMap(resultMap: HashMap<String, String>) = resultMap

    override fun getAmericanAge(birthDate: String?): Int {
        val now = LocalDate.now()
        val parsedBirthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd"))
        var americanAge = now.minusYears(parsedBirthDate.year.toLong()).year // (1)

        // (2)
        // 생일이 지났는지 여부를 판단하기 위해 (1)을 입력받은 생년월일의 연도에 더한다.
        // 연도가 같아짐으로 생년월일만 판단할 수 있다!
        if (parsedBirthDate.plusYears(americanAge.toLong()).isAfter(now)) {
            americanAge = americanAge - 1
        }
        return americanAge
    }
}
