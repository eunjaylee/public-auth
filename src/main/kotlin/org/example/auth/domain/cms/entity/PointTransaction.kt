package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "point_transaction")
class PointTransaction(
    @Column(name = "user_seq", columnDefinition = "int NOT NULL")
    var userSeq: Long = 0,
    @Column(name = "post_action", columnDefinition = "INT")
    @Comment("포인트 증감 타입")
    var postAction: String = "", // C, R, U, D / 회원가입 / 계좌이체
    @Column(name = "point_amount", columnDefinition = "int NOT NULL")
    @Comment("포인트 증감 수량")
    var pointAmount: Int = 0,
    @Column(name = "point_balance", columnDefinition = "int NOT NULL")
    @Comment("현재 잔액") // 잔액이 0보다 큰 마지막 TxSeq부터 최근까지 합이 현재 balance이긴해
    var pointBalance: Int = 0,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_tx_seq", columnDefinition = "INT")
    @Comment("포인트 사용 고유 번호")
    var pointTxSeq: Long = 0,
) {
    // 매번 전체 sum을 할게 아니라면 범위를 줄일 수 있는 조건이 필요하다.
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
