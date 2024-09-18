package org.example.auth.config

import org.example.auth.domain.cms.dao.PointTransactionJpaDao
import org.example.auth.domain.cms.entity.PointTransaction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class UserRegProcess(
    private val pointTransactionJpaDao: PointTransactionJpaDao,
) {
    @Bean
    fun addPoint(): Consumer<Long> {
        return Consumer<Long> {
                msg ->
            run {
                pointTransactionJpaDao.save(
                    PointTransaction(
                        userSeq = msg,
                        pointBalance = 10000,
                        pointAmount = 10000,
                        postAction = "A",
                    ),
                )
                println("회원 포인트 지급완료........")
            }
        }
    }
}
