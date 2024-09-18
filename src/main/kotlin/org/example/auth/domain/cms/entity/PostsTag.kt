package org.example.auth.domain.cms.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "posts_tag")
class PostsTag(
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment("게시글 고유키값")
    var postSeq: Long = 0,
    @Column(name = "post_tag", columnDefinition = "VARCHAR(10)")
    @Comment("태그")
    var postTag: String = "",
    @Id
    @Column(name = "post_tag_seq", columnDefinition = "INT")
    @Comment("태그 번호")
    var postTagSeq: Long = 0,
)
