package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "board_type")
class Board(
    @Id
    @Column(name = "board_type", columnDefinition = "VARCHAR(20) NOT NULL ")
    @Comment(value = "게시글 타입")
    val boardType: String,
    @field:Length(max = 100)
    @Column(name = "board_name", columnDefinition = "VARCHAR(100) NOT NULL  ")
    @Comment(value = "게시판 명칭")
    var boardName: String = "",
    @Column(name = "use_yn", columnDefinition = "VARCHAR(1) NOT NULL ")
    @Comment(value = "사용여부")
    var useYn: String = "Y",
    @Column(name = "point_yn", columnDefinition = "VARCHAR(1) NOT NULL ")
    @Comment(value = "포인트 정책 사용여부")
    var pointYn: String = "Y",
    @Column(name = "manager_yn", columnDefinition = "VARCHAR(1) NOT NULL ")
    @Comment(value = "관리자 전용 여부")
    var managerYn: String = "Y",
    @Column(name = "read_point_amt", columnDefinition = "INT NOT NULL ")
    @Comment(value = "읽기 포인트 차감 수량")
    var readPointAmount: String = "Y",
    @Column(name = "write_point_amt", columnDefinition = "INT NOT NULL ")
    @Comment(value = "쓰기 포인트 차감 수량")
    var writePointAmount: String = "Y",
) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_at", updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @CreatedDate
    @Comment("등록일시")
    val createAt: LocalDateTime = LocalDateTime.now()

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "update_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ")
    @LastModifiedDate
    @Comment("수정일시")
    var updateAt: LocalDateTime? = null
}
