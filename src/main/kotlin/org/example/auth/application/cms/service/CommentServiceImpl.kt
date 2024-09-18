package org.example.auth.application.cms.service

import org.example.auth.adaptor.cms.dto.PostsCommentDto
import org.example.auth.domain.cms.dao.PostsCommentJpaDao
import org.example.auth.infrastructure.util.modelMapTo
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

class CommentServiceImpl(
    private val commentDao: PostsCommentJpaDao,
) : CommentService {
    override fun saveComment(comment: PostsCommentDto) {
        commentDao.save(comment.modelMapTo())
    }

    override fun getCommentList(
        postSeq: Long,
        pageable: Pageable,
    ): Slice<PostsCommentDto> {
        return commentDao.findByPostSeq(postSeq, pageable).map { it.modelMapTo<PostsCommentDto>() }
    }

    override fun deleteComment(commentSeq: Long) {
        return commentDao.deleteById(commentSeq)
    }
}
