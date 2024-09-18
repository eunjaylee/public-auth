package org.example.auth.domain.auth.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import org.example.auth.domain.auth.entity.auth.UserAuthorities
import org.example.auth.domain.auth.entity.enumtype.UserStatus
import org.hibernate.annotations.Comment
import org.hibernate.annotations.Formula
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user_detail")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "userSeq")
class UserDetail(
    @Column(name = "user_id", columnDefinition = "varchar(100) NOT NULL DEFAULT '' COMMENT '사용자 로그인 ID'")
    val userId: String = "",
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "user_pwd", columnDefinition = "varchar(300) NOT NULL DEFAULT '' COMMENT '로그인 패스워드'")
    var userPwd: String = "",
    @Column(name = "user_name", columnDefinition = "varchar(100) NOT NULL DEFAULT '' COMMENT '사용자 이름'")
    val userName: String = "",
    @Column(name = "recommend_code", columnDefinition = "varchar(5)  COMMENT '추천인 코드'")
    val recommendCode: String = "",
    @Id
    @Column(name = "user_seq", columnDefinition = "int NOT NULL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userSeq: Long = 0,
) {
    @Version
    var version: Long = 0

    @Column(name = "user_status", columnDefinition = "varchar(50) NOT NULL DEFAULT 'NORMAL' COMMENT '사용자 상태'")
    @Enumerated(EnumType.STRING)
    var userStatus: UserStatus = UserStatus.NORMAL

    @Column(name = "failed_login_attempts", nullable = false)
    var failedLoginAttempts: Int = 0

    @Column(name = "my_recommend_code", columnDefinition = "varchar(5)  COMMENT '내 추천인 코드'")
    var myRecommendCode: String = ""

    @Column(name = "account_locked", nullable = false)
    var accountLocked: Boolean = false

    @Column(name = "image_url", columnDefinition = "varchar(300) NULL COMMENT '이미지 경로'")
    var imageUrl: String? = null

    @Column(name = "email_verified", columnDefinition = "boolean COMMENT '이메일 인증 여부'")
    var emailVerified: Boolean? = true

    @Column(name = "expired_at", columnDefinition = "TIMESTAMP COMMENT '계정 활성화 기간'")
    var expiredAt: LocalDateTime? = LocalDateTime.now().plusDays(7)

    @Formula(
        "(select hist.login_dtime from user_login_hist hist where hist.user_seq = user_seq order by hist.user_login_hist_seq desc limit 1)",
    )
    var lastLoginDtime: LocalDateTime? = null

    @OneToMany(mappedBy = "userDetail", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val userAuthorities: MutableSet<UserAuthorities> = mutableSetOf()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_at", updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @CreatedDate
    @Comment("등록일시")
    var createAt: LocalDateTime = LocalDateTime.now()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "update_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @LastModifiedDate
    @Comment("수정일시")
    var updateAt: LocalDateTime = LocalDateTime.now()
}
