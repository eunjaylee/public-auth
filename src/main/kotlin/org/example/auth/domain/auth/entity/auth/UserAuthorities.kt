package org.example.auth.domain.auth.entity.auth

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import org.example.auth.domain.auth.entity.UserDetail
import java.io.Serializable

@Entity
@Table(name = "user_authorities", uniqueConstraints = [UniqueConstraint(columnNames = ["user_seq", "authority"])])
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "authoritySeq")
class UserAuthorities(
    @Id
    @Column(name = "authority_seq", columnDefinition = "int NOT NULL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var authoritySeq: Long = 0,
    @Column(name = "authority", columnDefinition = "varchar(50) NOT NULL")
    var authority: String = "",
) : Serializable {
    //    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false)
    var userDetail: UserDetail = UserDetail()
}
