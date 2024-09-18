package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PostsPeriod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostsPeriodJpaDao : JpaRepository<PostsPeriod, Long>
