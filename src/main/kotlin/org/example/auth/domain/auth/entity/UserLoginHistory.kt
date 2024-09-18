package org.example.auth.domain.auth.entity

import jakarta.persistence.*
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user_login_hist")
class UserLoginHistory(
    @Column(name = "user_seq", columnDefinition = "int NOT NULL COMMENT '유저 고유순번'")
    var userSeq: Long = 0,
    @Column(name = "device_type", columnDefinition = "varchar(100) NOT NULL DEFAULT '' COMMENT '기기 종류'")
    var deviceType: String = "",
    @Column(name = "device_os", columnDefinition = "varchar(100) NOT NULL DEFAULT '' COMMENT '기기 os'")
    var deviceOs: String = "",
    @Column(name = "device_is", columnDefinition = "varchar(100) NOT NULL DEFAULT '' COMMENT '기기 ip'")
    var deviceIp: String = "",
    @Column(name = "browser", columnDefinition = "varchar(100) NOT NULL DEFAULT '' COMMENT '접속 브라우져 정보'")
    var browser: String = "",
) {
    @Id
    @Column(name = "user_login_hist_seq", columnDefinition = "bigint NOT NULL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userLoginHistorySeq: Long = 0

    @LastModifiedDate
    @Column(name = "login_dtime", columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '로그인 일시'")
    var loginDtime: LocalDateTime = LocalDateTime.now()
}
