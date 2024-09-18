package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostActionPk
import org.example.auth.domain.cms.entity.PostReActionSummery
import org.springframework.data.jpa.repository.JpaRepository

interface PostReActionSummeryJpaDao : JpaRepository<PostReActionSummery, PostActionPk>
