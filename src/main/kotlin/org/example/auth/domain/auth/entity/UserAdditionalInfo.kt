package org.example.auth.domain.auth.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

@Embeddable
data class UserAdditionalInfo(
    @Column(name = "address1", columnDefinition = "varchar(500) NULL  ")
    @Comment("주소1")
    var address1: String? = null,
    @Column(name = "address2", columnDefinition = "varchar(500) NULL ")
    @Comment("주소2")
    var address2: String? = null,
    @Column(name = "zipcode", columnDefinition = "varchar(100) NULL  ")
    @Comment("우편번호")
    var zipcode: String? = null,
    @Column(name = "marketing_agree_yn", columnDefinition = "varchar(1) DEFAULT 'N'   ")
    @Comment("마케팅 동의 여부")
    var marketAgreeYn: String? = "N",
    @Column(name = "marketing_agree_dtime", columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP  ")
    @Comment("마케팅 동의 일시")
    var marketAgreeDtime: LocalDateTime? = null,
    @Column(name = "alarm_agree_yn", columnDefinition = "varchar(1) DEFAULT 'N'  ")
    @Comment("알림 동의 여부")
    var alarmYn: String? = "N",
    @Column(name = "alarm_agree_dtime", columnDefinition = "timestamp NULL DEFAULT CURRENT_TIMESTAMP ")
    @Comment("알림 동의 시간")
    var alarmAgreeDtime: LocalDateTime? = null,
    /**
     * TODO 어디에 있어야 할지 고민해 보자.. -> 암호화 불필요
     */
    @Column(name = "device_push_id", columnDefinition = "varchar(300) NOT NULL DEFAULT '' ")
    @Comment("기기 push id")
    var devicePushId: String = "",
) : java.io.Serializable
