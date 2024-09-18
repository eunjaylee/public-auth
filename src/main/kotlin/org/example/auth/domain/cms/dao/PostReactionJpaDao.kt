package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostReactionPk
import org.example.auth.domain.cms.entity.PostsReaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostReactionJpaDao : JpaRepository<PostsReaction, PostReactionPk>
