package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.UserPersonalInfo
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserPersonalInfoDao : JpaRepository<UserPersonalInfo, Long> {
    /**
     * nice에서 보내준 개인 구분정보값 중복 확인 CI
     */
    @EntityGraph(attributePaths = ["userDetail"])
    fun findByCi(ci: String): Optional<UserPersonalInfo>

    /**
     * 이메일 인증시 중복인지 확인해보자...
     */
//    fun findByUserAdditionalInfoEmail(email : String) : Optional<UserPersonalInfo>
    @EntityGraph(attributePaths = ["userDetail"])
    fun findByEmail(email: String): Optional<UserPersonalInfo>

    /**
     * 핸드폰이 있는지 체크한다.
     */
    @EntityGraph(attributePaths = ["userDetail"])
    fun findByCellPhone(cellPhone: String): Optional<UserPersonalInfo>
}
