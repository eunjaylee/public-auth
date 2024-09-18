package org.example.auth.domain.auth.entity.auth

import jakarta.persistence.*

@Entity
@Table(name = "url_access")
class UrlAccess(
    @Id
    @Column(name = "url_access_seq", columnDefinition = "int NOT NULL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var urlAccessSeq: Long = 0,
    @Column(name = "url_pattern", columnDefinition = "VARCHAR(50) NOT NULL")
    var urlPattern: String,
)
