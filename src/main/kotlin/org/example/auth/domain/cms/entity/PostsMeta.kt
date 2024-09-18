package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.io.Serializable

@Entity
@Table(name = "posts_meta")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "pk")
class PostsMeta(
    @Column(name = "post_meta_value", columnDefinition = "VARCHAR(500) ")
    @Comment("게시글 메타 값")
    var postMetaValue: String,
    @EmbeddedId
    val pk: PostMetaPk = PostMetaPk(),
) {
    @JsonIgnore
    @MapsId("postSeq")
    @ManyToOne
    @JoinColumn(name = "post_seq")
    @JsonBackReference
    var posts: Posts? = null
}

@Embeddable
data class PostMetaPk(
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment("게시글 고유키값")
    val postSeq: Long = 0,
    @Column(name = "post_meta_key", columnDefinition = "VARCHAR(100)")
    @Comment("게시글 메타 키")
    var postMetaKey: String = "",
) : Serializable
