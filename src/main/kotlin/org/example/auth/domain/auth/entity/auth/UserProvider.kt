package org.example.auth.domain.auth.entity.auth

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "user_provider")
class UserProvider(
    @Id
    val id: String = "",
    val providerId: String = "",
    val userName: String = "",
    val userSeq: Long = 0,
)
