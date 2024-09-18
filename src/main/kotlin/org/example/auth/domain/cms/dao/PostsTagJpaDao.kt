package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostsTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostsTagJpaDao : JpaRepository<PostsTag, Long>
