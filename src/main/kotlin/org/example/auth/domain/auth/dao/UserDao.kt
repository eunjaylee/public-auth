package org.example.auth.domain.auth.dao

import com.querydsl.core.Tuple
import org.example.auth.adaptor.auth.dto.AccountDto
import org.example.auth.adaptor.auth.dto.UserSearchParamReq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface UserDao : UserJpaDao {
    fun userSearch(
        param: UserSearchParamReq,
        page: Pageable,
    ): Page<AccountDto>

    fun getUserCountByAgeGroup(): List<Tuple>
}
