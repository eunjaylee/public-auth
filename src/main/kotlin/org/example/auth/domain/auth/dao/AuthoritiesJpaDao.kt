package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.auth.Authorities
import org.springframework.data.jpa.repository.JpaRepository

interface AuthoritiesJpaDao : JpaRepository<Authorities, String>
