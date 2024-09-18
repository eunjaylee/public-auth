package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import org.example.auth.adaptor.binder.UserIdSetAdvice
import org.example.auth.domain.cms.enumtype.PostStatus
import org.hibernate.annotations.Comment
import org.hibernate.annotations.Formula
import org.hibernate.envers.NotAudited
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
// @Audited
// @EntityListeners(AuditingEntityListener::class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "postSeq")
class Posts(
    @Column(name = "board_type", columnDefinition = "VARCHAR(20) NOT NULL ")
    @Comment("게시글 타입")
    var boardType: String = "",
    @Formula("(select type.board_name from board_type type where type.board_type = board_type)")
    @NotAudited
    var boardName: String = "",
    @Column(name = "post_title", columnDefinition = "VARCHAR(200) NOT NULL")
    @Comment("게시글 제목")
    var postTitle: String,
    @Column(name = "post_content", columnDefinition = "TEXT ")
    @Comment("게시글 내용")
    var postContent: String,
    @Column(name = "post_status", columnDefinition = "VARCHAR(20) NOT NULL  ")
    @Comment("게시글 상태")
    @Enumerated(EnumType.STRING)
    var postStatus: PostStatus = PostStatus.CREATE,
    @Column(name = "tag", columnDefinition = "VARCHAR(20) NOT NULL ")
    @Comment("태그")
    var tag: String = "",
    // 언제필요하지?? FAQ, Q&A등에서
    @Column(name = "order_seq", columnDefinition = "INT  ")
    @Comment("게시글 순서")
    var orderSeq: Int = 0,
    @Column(name = "display_yn", columnDefinition = "VARCHAR(1) NOT NULL  ")
    @Comment("노출여부")
    var displayYn: String = "Y",
    @Column(name = "user_seq", columnDefinition = "INT")
    @Comment("유저 고유 순번")
    override var userSeq: Long = 0,
    @Column(name = "employ_seq", columnDefinition = "INT  ")
    @Comment("직원 이메일 주소")
    override var employSeq: Long = 0,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment("게시글 고유키값")
    var postSeq: Long = 0,
) : UserIdSetAdvice {
    @Version
    var version: Int = 0

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_at", updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @CreatedDate
    @Comment("등록일시")
    var createAt: LocalDateTime = LocalDateTime.now()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "update_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @LastModifiedDate
    @Comment("수정일시")
    var updateAt: LocalDateTime = LocalDateTime.now()

    @OneToOne(mappedBy = "posts", cascade = [CascadeType.ALL])
    @NotAudited
    var postsPeriod: PostsPeriod? = null

    // 양방향
    @OneToMany(mappedBy = "posts", cascade = [CascadeType.ALL])
    @NotAudited
    var metaList: MutableList<PostsMeta>? = mutableListOf<PostsMeta>()

    // 단방향
    @OneToMany
    @JoinColumn(
        name = "post_seq",
        insertable = false,
        updatable = false,
        foreignKey =
            ForeignKey(
                value = ConstraintMode.NO_CONSTRAINT,
            ),
    )
    @NotAudited
    var attachList: MutableList<PostsAttachFile>? = mutableListOf<PostsAttachFile>()

    @Transient
    var attachSeqList: ArrayList<Long>? = ArrayList<Long>()
}
