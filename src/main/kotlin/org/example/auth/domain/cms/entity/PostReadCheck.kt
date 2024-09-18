package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "post_read_check", indexes = [Index(name = "idx_read_post_seq", columnList = "postSeq")])
class PostReadCheck(
    @EmbeddedId
    val pk: PostReadPk = PostReadPk(),
) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_at", updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @CreatedDate
    @Comment("등록일시")
    val createAt: LocalDateTime = LocalDateTime.now()
}

@Embeddable
data class PostReadPk(
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment("게시글 고유키값")
    val postSeq: Long = 0,
    @Column(name = "user_seq", columnDefinition = "INT NOT NULL")
    @Comment("회원 고유 키값")
    var userSeq: Long = 0,
) : Serializable
