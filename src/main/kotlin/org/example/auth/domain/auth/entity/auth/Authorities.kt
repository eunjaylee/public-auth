package org.example.auth.domain.auth.entity.auth

import jakarta.persistence.*

@Entity
@Table(name = "authorities") // , uniqueConstraints = [UniqueConstraint(columnNames = ["authority"])])
class Authorities(
    @Id
    @Column(name = "authority", columnDefinition = "varchar(50) NOT NULL")
    var authority: String = "",
)
