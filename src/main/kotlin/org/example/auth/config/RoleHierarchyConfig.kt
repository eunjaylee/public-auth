package org.example.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl

@Configuration
class RoleHierarchyConfig {
    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val roleHierarchy = RoleHierarchyImpl()
        val hierarchy = "ROLE_ADMIN > ROLE_USER\nROLE_USER > ROLE_GUEST" // 역할 계층 정의
        roleHierarchy.setHierarchy(hierarchy)
        return roleHierarchy
    }
}
