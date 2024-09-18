package org.example.auth.domain.auth.entity.enumtype

enum class CommonCode(val message: String) {
    // 공통
    SUCCESS("정상 처리됨"),
    REQUEST_NOT_VALID("요청한 값이 올바르지 않음"),
    RESOURCE_NOT_FOUND("요청한 리소스가 없음"),
    UNAUTHORIZED("인증이 되어 있지 않음"),
    FORBIDDEN("권한 부족"),
    SERVER_ERROR("서버 오류"),

    // 회원관련
    USER_ALREDY_EXIST("이미 가입된 회원"),
    USER_NOT_FOUND("회원 정보를 찾을 수 없습니다."),
    USER_NOT_VALID("탈퇴 처리된 회원입니다."),
    SESSION_NOT_FOUND("세션 정보를 찾을 수 없습니다."),
    SESSION_NOT_VALID("세션값이 일치 하지 않습니다."),
    USER_NOT_NATIVE("내국인이 아닙니다."),
    USER_NOT_ADULT("성인이 아닙니다."),
    USER_ANOTHER_LOGIN("다른 장치에서 로그인함"),

    // 인증관련
    LOGIN_IN("인증이 되었습니다."),
    LOGIN_OUT("인증된 상태가 아닙니다."),
    LOGIN_FAIL("인증실패 ID/PWD 오류"),
    OAUTH2_EMAIL_NOT_FOUND("OAuth2 공급자에서 이메일 정보를 찾을 수 없습니다."),
    OAUTH2_PROVIDER_INVALID("OAuth2 공급자가 기존 로그인 정보와 일치하지 않습니다."),
    OAUTH2_PROVIDER_NOT_SUPPORTED("지원되는 OAuth2 공급자가 아닙니다."),

    // 펀딩관련
    FUND_NOT_FOUND("펀드 상품 존재안함"),
    FUND_ALERADY_EXISTS("이미 존재하는 상품"),

    // 뱅킹관련
    BANK_NOT_AVAILABLE("은행 서비스 시간 아닙니다."),
    ACCOUNT_FAIL("계좌 인증 실패"),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일"),

    // 투자관련
    INVEST_UNAVAILABLE("투자 가능항 상품이 아닙니다."),

    // Message 관련
    SES_FAIL("아마존 메일 발송이 실패했습니다."),
    SES_SUCCESS("아마존 메일 발송이 성공했습니다."),
}
