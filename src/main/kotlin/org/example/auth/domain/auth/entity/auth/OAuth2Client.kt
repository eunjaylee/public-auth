package org.example.auth.domain.auth.entity.auth

import jakarta.persistence.*

@Entity
@Table(name = "oauth2_client")
class OAuth2Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    val registrationId: String? = null, // 클라이언트 식별자
    @Column(nullable = false)
    val clientId: String? = null,
    @Column(nullable = false)
    val clientSecret: String? = null,
    @Column(nullable = false)
    val clientName: String? = null,
    @Column
    val authorizationGrantType: String? = null,
    @Column(nullable = false)
    val redirectUri: String? = null,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "oauth2_client_scope", joinColumns = [JoinColumn(name = "client_id")])
    @Column(name = "scope")
    val scopes: Set<String>? = null,
    @Column
    val authorizationUri: String? = null,
    @Column
    val tokenUri: String? = null,
    @Column
    val userInfoUri: String? = null,
    @Column
    val clientAuthenticationMethod: String? = null,
    @Column
    val userNameAttributeName: String? = null,
    @Column
    val jwkSetUri: String? = null,
    @Column
    val issuerUri: String? = null,
)
