package org.example.auth.domain.auth.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_role")
class UserRole(
    @Column(name = "user_seq", columnDefinition = "int NOT NULL")
    var userSeq: Long = 0,
    @Column(name = "authority_seq", columnDefinition = "int NOT NULL")
    var authoritySeq: Long = 0,
) {
    @Id
    @Column(name = "user_role_seq", columnDefinition = "int NOT NULL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userRoleSeq: Long = 0
}
