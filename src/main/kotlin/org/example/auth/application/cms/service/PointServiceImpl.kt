package org.example.auth.application.cms.service

import org.example.auth.domain.cms.dao.PointOrderJpaDao
import org.example.auth.domain.cms.dao.PointTransactionJpaDao
import org.example.auth.domain.cms.entity.PointOrder
import org.example.auth.domain.cms.entity.PointTransaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PointServiceImpl(
    private val pointOrderJpaDao: PointOrderJpaDao,
    private val pointTransactionJpaDao: PointTransactionJpaDao,
) : PointService {
    override fun getPointOrder(orderSeq: Long): Optional<PointOrder> {
        return pointOrderJpaDao.findById(orderSeq)
    }

    override fun makePointOrder(pointOrder: PointOrder) {
        pointOrderJpaDao.save(pointOrder)
    }

    override fun cancelPointOrder(orderSeq: Long) {
        TODO("Not yet implemented")
        // 주문을 가져와서 이미 승인 상태면 취소주문만 쌓는다 -> 환불개념
        // 주문을 가져와서 승인정 상태면 원주문을 취소하고 해당주문을 쌓는다.
    }

    override fun getOrderList(
        orderParam: Any,
        page: Pageable,
    ): Page<PointOrder> {
        TODO("Not yet implemented")
        return pointOrderJpaDao.findByUserSeq(1, page)
    }

    override fun getAdminOrderList(
        orderParam: Any,
        page: Pageable,
    ): Page<PointOrder> {
        TODO("Not yet implemented")
    }

    override fun doPointTx(pointTransaction: PointTransaction) {
        TODO("Not yet implemented")
    }

    override fun doPointOrder(
        orderSeq: Long,
        state: String,
    ) {
        TODO("Not yet implemented")
    }

    override fun getPointBalance(): Long {
        TODO("Not yet implemented")
    }
}
