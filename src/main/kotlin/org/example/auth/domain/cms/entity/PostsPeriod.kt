package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

@Entity
@Table(name = "posts_period")
class PostsPeriod(
    @field:DateTimeFormat(pattern = "yyyy.MM.dd HH")
    @Column(name = "started_at", columnDefinition = "TIMESTAMP ")
    @Comment("이벤트 시작시간")
    var startedAt: Date,
    @field:DateTimeFormat(pattern = "yyyy.MM.dd HH")
    @Column(name = "finished_at", columnDefinition = "TIMESTAMP")
    @Comment("이벤트 종료시간")
    var finishedAt: Date,
    @Id
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment("게시글 고유키값")
    var postSeq: Long = 0,
) {
    @JsonIgnore
    @MapsId
    @OneToOne()
    @JoinColumn(name = "post_seq")
    var posts: Posts? = null
}
