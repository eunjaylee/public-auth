package org.example.auth.adaptor.auth.dto

data class NiceRequest(
    val sSiteCode: String, // NICE로부터 부여받은 사이트 코드
    val sSitePassword: String, // NICE로부터 부여받은 사이트 패스워드
    // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
    // 리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
    var sReturnUrl: String = "", // 성공시 이동될 URL
    var sErrorUrl: String = "", // 실패시 이동될 URL
    var sRequestNumber: String, // 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로
    // 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
    var sAuthType: String = "M", // 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
    var popgubun: String = "Y", // Y : 취소버튼 있음 / N : 취소버튼 없음
    var customize: String = "Mobile", // 없으면 기본 웹페이지 / Mobile : 모바일페이지
    var sGender: String = "0", // 없으면 기본 선택 값, 0 : 여자, 1 : 남자
) {
    // 입력될 plain 데이타를 만든다.
    fun getPlainData() =
        "7:REQ_SEQ" + sRequestNumber.toByteArray().size + ":" + sRequestNumber +
            "8:SITECODE" + sSiteCode.toByteArray().size + ":" + sSiteCode +
            "9:AUTH_TYPE" + sAuthType.toByteArray().size + ":" + sAuthType +
            "7:RTN_URL" + sReturnUrl.toByteArray().size + ":" + sReturnUrl +
            "7:ERR_URL" + sErrorUrl.toByteArray().size + ":" + sErrorUrl +
            "11:POPUP_GUBUN" + popgubun.toByteArray().size + ":" + popgubun +
            "9:CUSTOMIZE" + customize.toByteArray().size + ":" + customize +
            "6:GENDER" + sGender.toByteArray().size + ":" + sGender
}
