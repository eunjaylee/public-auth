package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.example.auth.adaptor.binder.UserIdSetAdvice
import org.hibernate.annotations.Comment
import java.time.LocalDateTime

@Entity
@Table(name = "posts_comment")
class PostsComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq", columnDefinition = "INT NOT NULL ")
    @Comment(value = "게시글 코멘트 고유번호")
    val commentSeq: Long = 0,
    @Column(name = "post_seq", columnDefinition = "INT NOT NULL  ")
    @Comment(value = "게시글 고유번호")
    var postSeq: Long = 0,
    @Column(name = "comment_title", columnDefinition = "VARCHAR(100) NOT NULL  ")
    @Comment(value = "게시글 제목")
    var commentTitle: String,
    @Column(name = "comment_content", columnDefinition = "TEXT  ")
    @Comment(value = "게시글 내용")
    var commentContent: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @Comment(value = "등록일시")
    var createAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "user_seq", columnDefinition = "INT  NOT NULL ")
    @Comment(value = "유저 고유 순번")
    override var userSeq: Long = 0,
    @Column(name = "employ_seq", columnDefinition = "INT ")
    @Comment(value = "직원 고유 순번")
    override var employSeq: Long = 0,
) : UserIdSetAdvice
