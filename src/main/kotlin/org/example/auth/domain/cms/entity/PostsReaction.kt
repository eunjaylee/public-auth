package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "post_reaction")
class PostsReaction(
    @EmbeddedId
    var pk: PostReactionPk = PostReactionPk(),
) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_at", updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @CreatedDate
    @Comment("등록일시")
    var createAt: LocalDateTime = LocalDateTime.now()
}

@Embeddable
data class PostReactionPk(
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment("게시글 고유키값")
    val postSeq: Long = 0,
    @Column(name = "user_seq", columnDefinition = "INT")
    @Comment("회원 번호")
    var userSeq: String = "",
    @Column(name = "post_reaction_type", columnDefinition = "VARCHAR(1)")
    @Comment("리액션 타입") // 좋아요 실어요 이미 있었으면 삭제하자
    var postReactionType: String = "",
) : Serializable
