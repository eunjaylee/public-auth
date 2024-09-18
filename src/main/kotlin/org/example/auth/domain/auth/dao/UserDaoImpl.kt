package org.example.auth.domain.auth.dao

import com.querydsl.core.Tuple
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.example.auth.adaptor.auth.dto.AccountDto
import org.example.auth.adaptor.auth.dto.UserSearchParamReq
import org.example.auth.domain.auth.entity.QUserDetail
import org.example.auth.domain.auth.entity.QUserPersonalInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Repository
class UserDaoImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val userJpaDao: UserJpaDao,
) : UserDao, UserJpaDao by userJpaDao {
    val table: QUserDetail = QUserDetail.userDetail

    private fun postCondition(param: UserSearchParamReq) =
        arrayOf(
            param.userId?.let {
                table.userId.like("$it%")
            },
            param.userName?.let {
                table.userId.like("$it%")
            },
            param.email?.let {
                QUserPersonalInfo.userPersonalInfo.email.like("$it%")
            },
            param.recommendCode?.let {
                table.recommendCode.eq(it)
            },
            param.startDt?.let {
                table.createAt.after(
                    LocalDateTime.parse("${it}T00:00:00.000", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                )
            },
            param.endDt?.let {
                table.createAt.before(
                    LocalDateTime.parse("${it}T23:59:59.999", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                )
            },
        )

    override fun userSearch(
        param: UserSearchParamReq,
        pageable: Pageable,
    ): Page<AccountDto> {
        val condition = postCondition(param)
//        AuthenticationProvider
        val result =
            jpaQueryFactory.select(
                Projections.constructor(
                    AccountDto::class.java,
                    table,
                    QUserPersonalInfo.userPersonalInfo,
                ),
            ).from(table)
                .join(QUserPersonalInfo.userPersonalInfo)
                .on(table.userSeq.eq(QUserPersonalInfo.userPersonalInfo.userSeq))
                .where(*condition)
                .orderBy(table.userSeq.asc())
                .offset(pageable.pageSize.toLong() * pageable.pageNumber)
                .limit(pageable.pageSize.toLong() + 1)
                .fetch()

        val totalCount =
            jpaQueryFactory.select(table.count()).from(table)
                .where(*condition)
                .fetchOne()

        return PageImpl(result, pageable, totalCount ?: 0)
    }

    override fun getUserCountByAgeGroup(): List<Tuple> {
        // Calculate the age based on birth_date
        val age =
            Expressions.numberTemplate(
                Int::class.java,
                "FLOOR(DATEDIFF(CURDATE(), {0}) / 365.25)",
                QUserPersonalInfo.userPersonalInfo.birthDate,
            )

        // Define the age group case statement
        val ageGroup =
            CaseBuilder()
                .`when`(age.between(0, 9)).then("0-9")
                .`when`(age.between(10, 19)).then("10-19")
                .`when`(age.between(20, 29)).then("20-29")
                .`when`(age.between(30, 39)).then("30-39")
                .`when`(age.between(40, 49)).then("40-49")
                .`when`(age.between(50, 59)).then("50-59")
                .`when`(age.between(60, 69)).then("60-69")
                .`when`(age.between(70, 79)).then("70-79")
                .otherwise("80+")

        // Construct the query
        return jpaQueryFactory
            .select(ageGroup, QUserPersonalInfo.userPersonalInfo.count())
            .from(QUserPersonalInfo.userPersonalInfo)
            .groupBy(ageGroup)
            .fetch()
    }
}
