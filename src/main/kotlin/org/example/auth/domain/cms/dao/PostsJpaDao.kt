package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.Posts
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostsJpaDao : JpaRepository<Posts, Long> {
    fun findByPostSeqAndBoardType(
        postSeq: Long,
        boardType: String,
    ): Optional<Posts>

    fun findByBoardTypeOrderByPostSeqDesc(
        boardType: String,
        pageable: Pageable,
    ): Page<Posts>
}
