package org.example.auth.domain.auth.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.example.auth.domain.auth.entity.converter.StringCryptoConverter
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user_personal_info")
class UserPersonalInfo(
    @Id
    @Column(name = "user_seq", columnDefinition = "int NOT NULL COMMENT '유저 고유순번' ")
    var userSeq: Long = 0,
    @Column(name = "email", columnDefinition = "varchar(500) NULL  ")
    @Comment("이메일")
    var email: String? = null,
    @Column(name = "cell_phone", columnDefinition = "varchar(50) NULL COMMENT '휴대전화'")
    var cellPhone: String? = null,
    @Column(name = "birth_date", columnDefinition = "varchar(50) NULL COMMENT '생년월일'")
    var birthDate: String? = null,
    @Column(name = "gender", columnDefinition = "varchar(1) NULL COMMENT '성별'")
    var gender: String? = null,
    @Column(name = "national_info", columnDefinition = "varchar(1) NULL COMMENT '내국인정보'")
    var nationalInfo: String? = null,
    @Column(name = "mobile_corp", columnDefinition = "varchar(10) NULL COMMENT '통신사'")
    var moblieCorp: String? = null,
    @Column(name = "ci", columnDefinition = "varchar(500) NULL COMMENT 'ci'")
    @Convert(converter = StringCryptoConverter::class)
    var ci: String? = null,
    @Column(name = "di", columnDefinition = "varchar(500) NULL COMMENT 'di'")
    @Convert(converter = StringCryptoConverter::class)
    var di: String? = null,
) : java.io.Serializable {
    @Embedded
    var userAdditionalInfo: UserAdditionalInfo = UserAdditionalInfo()

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

    @OneToOne(fetch = FetchType.LAZY, targetEntity = UserDetail::class, cascade = arrayOf(CascadeType.DETACH))
    @JoinColumn(name = "userSeq", updatable = false, insertable = false, foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    var userDetail: UserDetail? = null
}
