package org.example.auth.adaptor.cms.dto

import org.example.auth.domain.cms.enumtype.PostStatus
import java.time.LocalDateTime

class PostsListReq(
    var postSeq: Long = 0,
    var postTitle: String? = null,
    var postStatus: PostStatus = PostStatus.CREATE,
    var orderSeq: Int = 0,
    var displayYn: String = "Y",
    var createAt: LocalDateTime? = null,
    var employSeq: Long = 0,
    var userSeq: Long = 0,
)
