package org.example.auth.adaptor.cms.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.example.auth.domain.cms.enumtype.PostStatus
import java.time.LocalDateTime

@Schema(description = "게시글 정보")
open class PostsDto {
    @Schema(description = "게시글 번호", example = "1")
    var postSeq: Long = 0

    @Schema(description = "트랜잭션 처리를 위한 버젼", example = "1")
    var version: Long = 0

    @Schema(description = "게시판 타입", example = "FAQ")
    var boardType: String = ""

    @Schema(description = "게시판 명칭 ", example = "문의 답변")
    var boardName: String? = null

    @field:Size(min = 3, max = 200, message = "{Size}")
    @Schema(description = "게시글 제목", example = "게시글 제목")
    var postTitle: String? = null

    @field:NotNull(message = "{NotNull}")
    @Schema(description = "게시글 내목", example = "게시글 내용")
    var postContent: String? = null

    @Schema(description = "게시글 상태", example = "CREATE")
    var postStatus: PostStatus? = PostStatus.CREATE

    @Schema(description = "태그", example = "JAVA, PYTHON")
    var tag: String? = ""

    @Schema(description = "게시글 순서", example = "1")
    var orderSeq: Int? = 0

    @Schema(description = "게시글 노출여부", example = "Y")
    var displayYn: String? = "Y"

    @Schema(description = "등록일시", example = "2024.08.01")
    var regDtime: LocalDateTime? = null

    @Schema(description = "수정일시", example = "2024.08.01")
    var modifyDtime: LocalDateTime? = null

    @Schema(description = "작성자 ID", example = "1")
    var userSeq: Long? = 0

    @Schema(description = "관리자 ID", example = "1")
    var employSeq: Long? = 0

    @Schema(description = "팝업설정", example = "")
    var postsPeriod: PostsPeriodDto? = null

    @Schema(description = "기타 속성", example = "")
    var metaList: MutableList<PostsMetaDto>? = null

    @Schema(description = "첨부파일 목록", example = "")
    var attachList: MutableList<PostsAttachFileDto>? = null

    @Schema(description = "첨부파일 키", example = "")
    var attachSeqList: ArrayList<Long>? = null
}
