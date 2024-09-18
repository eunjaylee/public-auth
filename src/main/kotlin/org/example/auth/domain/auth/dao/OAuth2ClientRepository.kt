package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.auth.OAuth2Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OAuth2ClientRepository : JpaRepository<OAuth2Client, Long> {
    fun findByRegistrationId(registrationId: String): Optional<OAuth2Client>
}
