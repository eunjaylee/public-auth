package org.example.auth.adaptor.cms.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.example.auth.domain.cms.entity.PostMetaPk

@Schema(description = "게시글 메타 정보를 담고 있는 객체")
class PostsMetaDto(
    @Schema(description = "메타 키", example = "1")
    var pk: PostMetaPk = PostMetaPk(),
    @Schema(description = "메타 값", example = "value")
    var postMetaValue: String = "",
)
