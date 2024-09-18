package org.example.auth.application.cms.service

import org.example.auth.domain.cms.entity.PointOrder
import org.example.auth.domain.cms.entity.PointTransaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface PointService {
    /**
     * 주문내역 상세보기
     */
    fun getPointOrder(orderSeq: Long): Optional<PointOrder>

    /**
     * 주문 생성
     */
    fun makePointOrder(pointOrder: PointOrder)

    /**
     * 주문 취소 --> 취소 승인이 필요하다? 승인이 필요없이 취소되는 경우도 있다.
     */
    fun cancelPointOrder(orderSeq: Long)

    /**
     * 포인트 주문 리스트 가져오기 - 개인꺼와 관리자를 분리할까?
     */
    fun getOrderList(
        orderParam: Any,
        page: Pageable,
    ): Page<PointOrder>

    /**
     * 관리자 포인트 주문 리스트 가져오기
     */
    fun getAdminOrderList(
        orderParam: Any,
        page: Pageable,
    ): Page<PointOrder>

    /**
     * 포인트 증감처리
     */
    fun doPointTx(pointTransaction: PointTransaction)

    /**
     * 주문 승인 취소
     */
    fun doPointOrder(
        orderSeq: Long,
        state: String,
    )

    /**
     * 내 포인트 잔고 보여주기 - tx 테이블 이용
     */
    fun getPointBalance(): Long
}
