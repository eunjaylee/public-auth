package org.example.auth.domain.cms.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class TimeStampEntity(
    @CreatedDate
    val createdAt: LocalDateTime? = null,
    @CreatedBy
    val createdBy: Int? = null,
    @LastModifiedDate
    val modifiedAt: LocalDateTime? = null,
    @LastModifiedBy
    val modifiedBy: Int? = null,
)
