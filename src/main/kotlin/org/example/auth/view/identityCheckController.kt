package org.example.auth.view

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import mu.KLogging
import org.example.auth.application.auth.service.IdentificationService
import org.example.auth.domain.auth.entity.enumtype.CommonCode
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/identity")
@Controller
class IdentityCheckController(
    val identificationService: IdentificationService,
//    val writeObjectMapper : ObjectMapper
) {
    companion object : KLogging()

    val writeObjectMapper: ObjectMapper = ObjectMapper()

    /**
     * 본인인증 창 요청값은 없다.
     */
    @Operation(summary = "본인확인 화면 요청")
    @GetMapping("req")
    fun checkIdentity(
        @RequestParam reqNum: String?,
        request: HttpServletRequest,
        model: HashMap<String, Any>,
    ): String {
        var mapper = ObjectMapper()
        logger.debug("check cookie : {}", mapper.writeValueAsString(request.cookies))

        var encData = identificationService.encCipherData(session = request.session, reqNum = reqNum)
        model.put("encData", encData)
        logger.debug("reqNum : {}", reqNum)
        logger.debug("encData : {}", encData)
        logger.debug("session : {}", request.session.getAttribute("REQ_SEQ"))

        return "/req"
    }

    /**
     * 이미 가입된 User의 경우 로그인 처리를 해준다. -> 응답으로 로그인 완료
     * 미가입시 본인인증 정보를 세션에 저장하고  본인인증 성공 이라고 결과 제공
     */
    @Operation(summary = "본인확인 성공시 Nice에서 호출")
    @RequestMapping("check")
    fun checkRun(
        @RequestParam EncodeData: String,
        request: HttpServletRequest,
        model: HashMap<String, Any>,
    ): Any {
        logger.debug("session REQ_SEQ2 : {}", request.session.getAttribute("REQ_SEQ"))
        logger.debug("session lastTime : {}", request.session.lastAccessedTime)
        logger.debug("nice result : {}", EncodeData)

        var userDetail: Map<String, String>? = null

        try {
            userDetail =
                identificationService.decEncodeData(EncodeData, request.session)
        } catch (e: Exception) {
            model.put("data", writeObjectMapper.writeValueAsString(mapOf("message" to e.message!!)))
            return "/check"
        }

        logger.debug("userPersonalInfo result : {}", userDetail)

//        var mapper = ObjectMapper()
//        logger.debug("check cookie : {}", mapper.writeValueAsString(request.cookies))
//
//        var strCode =  CommonCode.SUCCESS.name
//        // user seq 가 0보다 크면 이미 회원인데 본인인증 받은 경우
//        if(userDetail.userSeq > 0) {
//            strCode = CommonCode.USER_ALREDY_EXIST.name
//
//            logger.debug("userStatus result : {}", userPersonalInfo.userDetail!!.userStatus)
//            logger.debug("userDetail modifyDtime : {}", userPersonalInfo.userDetail!!.modifyDtime)
//
//            if(userPersonalInfo.userDetail!!.userStatus == UserStatus.DROPOUT) {
//                strCode = CommonCode.USER_NOT_VALID.name
//                var desc = userPersonalInfo.userDetail!!.modifyDtime!!
//                    .plusDays(30).toString() + " 일 이후로 재 가입 가능합니다."
//
//                model.put("data", writeObjectMapper.writeValueAsString(mapOf("strCode" to strCode, "message" to CommonCode.USER_NOT_VALID.message, "data" to desc)))
//                return "/check"
//            }
//        }
//
//        model.put("data", writeObjectMapper.writeValueAsString(userPersonalInfo))
        logger.debug("data : {}", model.get("data"))
        return "/check"
    }

    /**
     * 이미 가입된 User의 경우 로그인 처리를 해준다. -> 응답으로 로그인 완료
     * 미가입시 본인인증 정보를 세션에 저장하고  본인인증 성공 이라고 결과 제공
     */
    @Operation(summary = "본인확인 성공시 Nice에서 호출")
    @RequestMapping("/checkAdult")
    fun checkAdult(
        @RequestParam EncodeData: String,
        request: HttpServletRequest,
        model: HashMap<String, Any>,
    ): Any {
        throw Exception(CommonCode.USER_NOT_ADULT.toString())
        return "/check"
    }

    /**
     * 이미 가입된 User의 경우 로그인 처리를 해준다. -> 응답으로 로그인 완료
     * 미가입시 본인인증 정보를 세션에 저장하고  본인인증 성공 이라고 결과 제공
     */
    @Operation(summary = "본인확인 성공시 Nice에서 호출")
    @RequestMapping("/checkNative")
    fun checkNative(
        @RequestParam EncodeData: String,
        request: HttpServletRequest,
        model: HashMap<String, Any>,
    ): Any {
        throw Exception(CommonCode.USER_NOT_NATIVE.toString())
        return "/check"
    }
}
