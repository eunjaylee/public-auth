package org.example.auth.domain.cms.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "point_order")
@Comment("포인트 주문 테이블")
class PointOrder(
    @Column(name = "user_seq", columnDefinition = "INT")
    @Comment("회원 번호")
    var userSeq: Long = 0,
    @Column(name = "order_amount", columnDefinition = "INT")
    @Comment("주문 금액")
    var orderAmount: Int = 0,
    // TODO enum
    @Column(name = "order_state", columnDefinition = "VARCHAR(10)")
    @Comment("주문 상태")
    var orderState: String = "",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_order_seq", columnDefinition = "INT")
    @Comment("포인트 주문번호")
    var pointOrderSeq: Long = 0,
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
