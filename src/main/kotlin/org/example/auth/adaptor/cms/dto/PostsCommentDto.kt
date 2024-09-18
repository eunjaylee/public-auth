package org.example.auth.adaptor.cms.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.example.auth.adaptor.binder.UserIdSetAdvice
import java.time.LocalDateTime

@Schema(description = "댓글 정보")
class PostsCommentDto : UserIdSetAdvice {
    @Schema(description = "댓글 번호", example = "1")
    val commentSeq: Long = 0

    @Schema(description = "게시글 번호", example = "1")
    var postSeq: Long = 0

    @Schema(description = "댓글 제목", example = "제목")
    var commentTitle: String? = null

    @field:NotNull(message = "{NotNull}")
    @Schema(description = "댓글 내용", example = "내용")
    var commentContent: String? = null

    @Schema(description = "댓글 작성일시", example = "2024.08.01")
    var createAt: LocalDateTime = LocalDateTime.now()

    @Schema(description = "댓글 작성자", example = "1")
    override var userSeq: Long = 0

    @Schema(description = "댓글 작성 직원", example = "1")
    override var employSeq: Long = 0
}
