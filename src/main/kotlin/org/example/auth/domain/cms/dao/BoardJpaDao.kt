package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardJpaDao : JpaRepository<Board, String> {
    fun findByBoardName(boardName: String): Board?

//    fun <T> findAll(type: Class<T>): Collection<T>
}
