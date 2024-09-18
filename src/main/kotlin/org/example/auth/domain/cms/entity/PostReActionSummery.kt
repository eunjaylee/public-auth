package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import java.io.Serializable

@Table(name = "post_reaction_summery")
@Entity
class PostReActionSummery(
    @Column(name = "post_action_cnt", columnDefinition = "INT")
    @Comment("액션 횟수")
    var postActionCnt: Long,
    @EmbeddedId
    val pk: PostActionPk = PostActionPk(),
) {
    @JsonIgnore
    @MapsId("postSeq")
    @ManyToOne
    @JoinColumn(name = "post_seq")
    var posts: Posts? = null
}

@Embeddable
data class PostActionPk(
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment("게시글 고유키값")
    val postSeq: Long = 0,
    @Column(name = "post_action_type", columnDefinition = "VARCHAR(1) ")
    @Comment("액션 타입")
    var postActionType: String = "",
) : Serializable
