package org.example.auth.domain.auth.entity.auth

import jakarta.persistence.*

@Entity
@Table(name = "role_hierarchy")
class RoleHierarchy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_hierarchy_seq")
    var roleHierarchySeq: Long = 0,
    @Column(name = "parent_role")
    var parentRole: String = "",
    @Column(name = "child_role")
    var childRole: String = "", // Getters and Setters
)
