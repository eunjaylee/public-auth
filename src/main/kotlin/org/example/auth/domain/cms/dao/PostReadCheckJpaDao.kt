package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostReadCheck
import org.example.auth.domain.cms.entity.PostReadPk
import org.springframework.data.jpa.repository.JpaRepository

interface PostReadCheckJpaDao : JpaRepository<PostReadCheck, PostReadPk>
