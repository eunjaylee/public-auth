package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.auth.RoleHierarchy
import org.springframework.data.jpa.repository.JpaRepository

interface RoleHierarchyJpaDao : JpaRepository<RoleHierarchy, Long>
