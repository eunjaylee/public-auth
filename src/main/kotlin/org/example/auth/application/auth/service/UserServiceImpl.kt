package org.example.auth.application.auth.service

// import org.springframework.cloud.stream.function.StreamBridge
import mu.KLogging
import org.example.auth.adaptor.auth.dto.*
import org.example.auth.domain.auth.Account
import org.example.auth.domain.auth.dao.*
import org.example.auth.domain.auth.entity.UserDetail
import org.example.auth.domain.auth.entity.UserLoginHistory
import org.example.auth.domain.auth.entity.UserPersonalInfo
import org.example.auth.domain.auth.entity.auth.UserProvider
import org.example.auth.domain.auth.entity.enumtype.CommonCode
import org.example.auth.infrastructure.util.modelMapTo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userDao: UserDao,
    private val loginHistoryDao: UserLoginHistoryDao,
    private val userPersonalInfoDao: UserPersonalInfoDao,
    private val notifyService: NotifyService,
    private val keyValueStore: KeyValueStore,
    private val passwordChangeHistoryJpaDao: PasswordChangeHistoryJpaDao,
    private val userProviderDao: UserProviderJpaDao,
    private val userLoginHistoryDao: UserLoginHistoryDao,

) : UserService, UserDetailsService {
    companion object : KLogging()

    override fun registUser(account: AccountDto): UserDetail {
//        if(account.isValidPwd()) Exception("패스워드가 잘못됐어")
//        if(!account.isValidVerifyCode()) Exception("코드 인증을 하지 않았어")
        if (!isNotDuplicateUserId(account.userId)) throw Exception("ID가 중복된 user라서 안됨")

        val accountInfo = account.accountProfileDto
        val userDetail = account.modelMapTo<UserDetail>()

        // 암호화된 비번을 입력해주고 ==> 나중에 이게 헷갈리지 않을까?
        userDetail.userPwd = passwordEncoder.encode(account.userPwd)
        userDao.save(userDetail)

        if (accountInfo != null) {
            account.accountProfileDto = null
            val userPersonalInfo = accountInfo.modelMapTo<UserPersonalInfo>()
            userPersonalInfo.userSeq = userDetail.userSeq
            // 단방향에서는 넣지 않아도 됨 넣으면 오히려 오류
//            userPersonalInfo.userDetail = userDetail
            userPersonalInfoDao.save(userPersonalInfo)
        }

        // todo 회원 가입시 가입 보너스 포인트를 부여한다.
//        streamBridge.send("registerUser", userDetail.userSeq)

        // 리턴은 userId, pwd -->  이정보로 로그인을 해줘야 함
        return userDetail
    }

    override fun registUser(
        account: AccountDto,
        accountProfileDto: AccountProfileDto?,
    ): UserDetail {
//        if(account.isValidPwd()) throw Exception("패스워드가 잘못됐어")
//        if(!account.isValidVerifyCode()) throw Exception("코드 인증을 하지 않았어")
        if (!isNotDuplicateUserId(account.userId)) throw Exception("ID가 중복된 user라서 안됨 ${account.userId}")

        val userDetail =
            account.modelMapTo<UserDetail>().apply {
                // 암호화된 비번을 입력해주고 ==> 나중에 이게 헷갈리지 않을까?
                userPwd = passwordEncoder.encode(account.userPwd)
            }

        // 회원 기본정보
        userDao.save(userDetail)

        // 회원 개인정보
        accountProfileDto?.let {
            val userPersonalInfo =
                accountProfileDto.modelMapTo<UserPersonalInfo>().apply {
                    userSeq = userDetail.userSeq
                }
            userPersonalInfoDao.save(userPersonalInfo)
        }

        // TODO : 회원 기본권한 셋팅 : ROLE_USER

        // TODO : 추천인 코드 생성

        // TODO 회원 가입시 가입 보너스 포인트를 부여한다.
//        streamBridge.send("registerUser", userDetail.userSeq)

        // 리턴은 userId, pwd -->  이정보로 로그인을 해줘야 함

        // TODO 가입과 동시에 로그인 처리는?
//        return loginUser(account.userId, account.password)
        return userDetail
    }

//    private fun loginUser(userId: String, password: String) : UserDetail {
//        val authenticationToken = UsernamePasswordAuthenticationToken(userId, password)
//        val authentication = authenticationManager.authenticate(authenticationToken)
//
//        SecurityContextHolder.getContext().authentication = authentication
//
//        return authentication.principal as UserDetail
//    }

    /**
     * 비밀번호 변경
     */
    override fun changePwd(account: Account): UserDetail {
        val userDetail = userDao.findById(account.userSeq).orElseThrow { Exception("") }
        userDetail.userPwd = getNewPwd(account)
        userDao.save(userDetail)

        // TODO 패스워드 변경 안내 메일 발송

        return userDetail
    }

    private fun getNewPwd(account: Account): String {
        val pwdList = passwordChangeHistoryJpaDao.findLimited3ByUserSeqOrderByPwdChangeSeqDesc(account.userSeq)
        val newPwd = passwordEncoder.encode(account.password)
        val filtered = pwdList.filter { it.pwd == newPwd }

        if (filtered.isNotEmpty()) {
            throw Exception("예전에 사용한적이 있는 비번은 사용불가능합니다.")
        }

        return newPwd
    }

    override fun changePwd(
        newPassword: String,
        idCode: String,
    ): UserDetail {
        val userId = keyValueStore.getKey(idCode) ?: throw Exception("사용할 수 없는 코드입니다.")

        val userDetail = userDao.findByUserId(userId).orElseThrow { Exception("해당 회원을 찾을 수 없습니다.") }

        userDetail.userPwd =
            getNewPwd(
                Account().apply {
                    userSeq = userDetail.userSeq
                    userPwd = newPassword
                },
            )
        userDao.save(userDetail)
        return userDetail
    }

//    SpringSecurity 인증과정에서 자동으로 비번 암호화를 교체한다.
//    fun updatePassword(userDetails: UserDetails, newPassword: String?): UserDetails {
//        // UserDetails에서 사용자 이름 가져오기
//        val username = userDetails.username
//
//
//        // 사용자 검색
//        val user: User = userRepository.findByUsername(username)
//            .orElseThrow { UsernameNotFoundException("User not found") }
//
//        // 암호 업데이트
//        userDetail.userPwd = passwordEncoder.encode(newPassword)
//        userRepository.save(user)
//
//        // 업데이트된 사용자 정보를 반환하기 위해 CustomUserDetails 생성
//        return CustomUserDetails(user)
//    }

    private fun historyPwd(
        newPassword: String,
        passwordHistory: List<String>,
    ) {
//        val passwordHistory = user.passwordHistory  // 이전에 사용했던 비밀번호 목록
        if (passwordHistory.any { passwordEncoder.matches(newPassword, it) }) {
            throw IllegalArgumentException("New password cannot be the same as previous passwords")
        }
    }

    /**
     * 로그인을 해주는 함수
     */
    private fun getAuthentication(userDetail: UserDetail): UsernamePasswordAuthenticationToken? {
        return try {
            // 모바일에 role는 member하나이다.
            val authorities = ArrayList<GrantedAuthority>()
//            userDetail.roleList.forEach { role -> authorities.add(SimpleGrantedAuthority(role.toString())) }
            UsernamePasswordAuthenticationToken(userDetail.modelMapTo<Account>(), null, authorities)
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * 회원 개인정보 가져오는 함수
     */
    @Transactional(readOnly = true)
    @Throws(Exception::class)
    override fun getUser(userSeq: Long): AccountDto? {
        val userPersonalInfo = userPersonalInfoDao.findById(userSeq)

        return if (userPersonalInfo.isPresent) {
            userPersonalInfo.get().userDetail!!.modelMapTo<AccountDto>().apply {
                accountProfileDto = userPersonalInfo.modelMapTo<AccountProfileDto>()
            }
        } else {
            return userDao.findById(userSeq)?.modelMapTo()
        }
    }

    /**
     * 회원 정보 수정 함수
     */
    @Throws(Exception::class)
    override fun modifyUserInfo(user: AccountDto): Boolean {
        logger.debug("user.userSeq : {}", user.userSeq)

        val userPersonalInfo =
            userPersonalInfoDao.findById(user.userSeq)
                .orElseThrow { Exception("user not found") }

//        userPersonalInfo.apply {
//            userAddtionalInfo = userAddtionalInfo?.let {
//                userInfo merge it
//            } ?: userInfo
//        }

        // null이 아닌것 만 업데이트를 하는방법
        userPersonalInfoDao.save(userPersonalInfo)
        return true
    }

    /**
     * id 중복여부 결과 리턴 --> TODO 오류코드로 줘야 하는지 검토 필요
     */
    override fun isNotDuplicateUserId(userId: String): Boolean {
        return userDao.findByUserId(userId).isEmpty
    }

    /**
     * 마케팅 동의 변경
     */
    @Throws(Exception::class)
    override fun modifyMarketingInfo(
        userInfo: Account,
        marketingYn: String,
    ): Boolean {
        val userPersonalInfo =
            userPersonalInfoDao.findById(userInfo.userSeq)
                .orElseThrow({ Exception(CommonCode.USER_NOT_FOUND.message) })
//        userPersonalInfo.userAddtionalInfo!!.marketAgreeYn = marketingYn
//        userPersonalInfo.userAddtionalInfo!!.marketAgreeDtime = LocalDateTime.now()
        userPersonalInfoDao.save(userPersonalInfo)
        return true
    }

    override fun sendVerifyCode(checkCodeReq: CheckCodeReq): Boolean {
        if (!checkCodeReq.verifyTo()) throw Exception("형식에 맞지 않습니다.")

        val verifyCode = Account.makeVerifyCode(6)

        val userPersonalInfo =
            when (checkCodeReq.sendType) {
                "email" -> userPersonalInfoDao.findByEmail(checkCodeReq.to)
                "cellPhone" -> userPersonalInfoDao.findByCellPhone(checkCodeReq.to)
                else -> Optional.empty()
            }

        if (checkCodeReq.type == "register" && userPersonalInfo.isPresent) {
            throw Exception("이미 가입된 내역이 있습니다.")
        } else if (checkCodeReq.type != "register" && userPersonalInfo.isEmpty) {
            throw Exception("가입된 이력이 없습니다.")
        }

        keyValueStore.setKey(checkCodeReq.to, "${checkCodeReq.type}_$verifyCode", 1800)
        notifyService.sendNotify(checkCodeReq.to, "[서비스 명]인증문자 발송", "[$verifyCode] 를 인증칸에 입력해 주세요")
        return true
    }

    override fun checkVerifyCode(checkCodeReq: CheckCodeReq): Boolean {
        // 타입과 코드가 일치했을때 일치이다. 회원정보에서 중복검사를 또 할 필요가 있을까?
        return keyValueStore.getKey(checkCodeReq.to) == "${checkCodeReq.type}_${checkCodeReq.checkCode}"
    }

    override fun idFind(idPwdReq: IdPwdReq): String {
        val userSeq = keyValueStore.getKey("verify_${idPwdReq.to}") ?: throw Exception("찾으려는 회원 정보가 없습니다.")
        val userDetail = userDao.findById(userSeq.toLong())

        return userDetail.get().userId
    }

    override fun pwdFind(idPwdReq: IdPwdReq): String {
        val userSeq = keyValueStore.getKey("verify_${idPwdReq.to}") ?: throw Exception("찾으려는 회원 정보가 없습니다.")
        val userDetail = userDao.findById(userSeq.toLong())

        // TODO : 임시 주소를 만들어 줘야함..
        val imsiCode = Account.makeVerifyCode(10)
        keyValueStore.setKey(imsiCode, userDetail.get().userId, 1800)
        return imsiCode
    }

    override fun getLoginHistoryList(userSeq: Long): Page<UserLoginHistory> {
        TODO("Not yet implemented")
    }

    override fun lastLoginHistory(): UserLoginHistory {
        TODO("Not yet implemented")
    }

    override fun getLoginHistory(
        userSeq: Long,
        page: Pageable,
    ): Page<UserLoginHistory> {
        return loginHistoryDao.findByUserSeq(userSeq, page)
    }

    /**
     * 마지막 비밀번호를 수정한 날짜
     */
    override fun passwordChangeDay(): LocalDate {
        TODO("Not yet implemented")
    }

    override fun userSearch(
        param: UserSearchParamReq,
        page: Pageable,
    ): Page<AccountDto> {
        return userDao.userSearch(param, page)
    }

    override fun getRecommendList(userSeq: Long): Slice<AccountDto>? {
        val user = userDao.findById(userSeq)
        if (user.isEmpty) throw Exception("회원 정보를 찾을 수 없습니다.")
        return userDao.findByRecommendCode(user.get().recommendCode).map { it.modelMapTo() }
    }

    @Transactional
    override fun loadUserByUsername(userId: String): UserDetails {
        logger.debug("CustomUserDetailsService loadUserByUsername : $userId")

        val userDetail =
            userDao.findByUserId(userId)
                .orElseThrow {
                    logger.error("User not found with userId : $userId")
                    Exception("USER_NOT_FOUND")
                }

        oauth2UserMapping(userDetail.userSeq)

        return userDetail.modelMapTo<Account>()
//        return Account.create(userDetail)
    }

    private fun oauth2UserMapping(userSeq: Long) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        if (authentication?.isAuthenticated == true &&
            authentication.authorities.contains(SimpleGrantedAuthority("ROLE_OAUTH"))
        ) {
            val account = authentication.principal as Account
            account.userId

            userProviderDao.save(UserProvider(providerId = account.providerId!!, id = account.userId, userSeq = userSeq))
        }
    }
}
