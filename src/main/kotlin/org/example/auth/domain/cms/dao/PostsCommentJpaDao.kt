package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostsComment
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostsCommentJpaDao : JpaRepository<PostsComment, Long> {
    fun findByPostSeq(
        postSeq: Long,
        pageable: Pageable,
    ): Slice<PostsComment>
}
