package org.example.auth.application.cms.service

import org.example.auth.adaptor.cms.dto.PostsCommentDto
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.security.access.prepost.PreAuthorize

interface CommentService {
    /**
     * 코멘트 저장
     */
    @PreAuthorize("hasPermission('', 'canWrite')")
    fun saveComment(comment: PostsCommentDto)

    /**
     * 코멘트 리스트 조회
     */
    @PreAuthorize("hasPermission('', 'canRead')")
    fun getCommentList(
        postSeq: Long,
        pageable: Pageable,
    ): Slice<PostsCommentDto>

    /**
     * 코멘트 삭제
     */
    @PreAuthorize("hasPermission('', 'canWrite')")
    fun deleteComment(commentSeq: Long)
}
