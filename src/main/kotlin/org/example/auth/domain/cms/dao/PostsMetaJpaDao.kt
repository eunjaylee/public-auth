package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostMetaPk
import org.example.auth.domain.cms.entity.PostsMeta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostsMetaJpaDao : JpaRepository<PostsMeta, PostMetaPk>
