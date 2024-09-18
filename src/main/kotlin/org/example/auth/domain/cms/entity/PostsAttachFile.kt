package org.example.auth.domain.cms.entity

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.hibernate.validator.constraints.Length

@Entity
@Table(name = "post_attach_file")
class PostsAttachFile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_seq", columnDefinition = "INT  ")
    @Comment(value = "첨부파일 고유키값")
    var attachSeq: Long = 0,
    @field:Length(max = 100)
    @Column(name = "file_key", columnDefinition = "VARCHAR(100) ")
    @Comment(value = "첨부파일 고유 키")
    var fileKey: String,
    @Column(name = "post_seq", columnDefinition = "INT")
    @Comment(value = "게시글 고유번호")
    var postSeq: Long = 0,
    @field:Length(max = 500)
    @Column(name = "file_name", columnDefinition = "VARCHAR(500)  ")
    @Comment(value = "첨부파일 이름")
    var fileName: String,
    @Column(name = "file_path", columnDefinition = "VARCHAR(500)  ")
    @Comment(value = "첨부파일 경로")
    var filePath: String,
    @Column(name = "file_size", columnDefinition = "Int ")
    @Comment(value = "첨부파일 크기 Byte")
    var fileSize: Long,
) {
    // ManyToOne 단방향??
}
