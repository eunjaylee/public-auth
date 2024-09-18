package org.example.auth.domain.auth.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "password_change_history")
class PasswordChangeHistory(
    @Column(name = "user_seq", columnDefinition = "int NOT NULL")
    var userSeq: Long = 0,
    @Column(name = "pwd", columnDefinition = "VARCHAR(500) NOT NULL")
    var pwd: String,
    @Id
    @Column(name = "pwd_change_seq", columnDefinition = "int NOT NULL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var pwdChangeSeq: Long = 0,
) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_at", updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @CreatedDate
    @Comment("등록일시")
    var createAt: LocalDateTime = LocalDateTime.now()
}
