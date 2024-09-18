package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostsAttachFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostsAttachFileJpaDao : JpaRepository<PostsAttachFile, Long> {
    fun findByPostSeq(postSeq: Long): List<PostsAttachFile>
}
