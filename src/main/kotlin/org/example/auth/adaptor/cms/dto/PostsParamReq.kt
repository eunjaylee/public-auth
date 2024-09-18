package org.example.auth.adaptor.cms.dto

import io.swagger.v3.oas.annotations.media.Schema

data class PostsParamReq(
    @Schema(description = "게시판 종류", example = "123")
    var boardType: String? = null,
    @Schema(description = "작성자 ID", example = "123")
    var userSeq: Long = 0,
    @Schema(description = "게시글의 고유 식별자", example = "123")
    var postSeq: Long? = null,
    @Schema(description = "글 노출 설정여부", example = "123")
    var displayYn: String? = "Y",
    @Schema(description = "검색 키워드", example = "123")
    var searchKeyword: String? = null,
)
