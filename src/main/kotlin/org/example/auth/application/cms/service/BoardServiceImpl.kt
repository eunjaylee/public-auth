package org.example.auth.application.cms.service

import org.example.auth.adaptor.cms.dto.BoardDto
import org.example.auth.domain.cms.dao.BoardJpaDao
import org.example.auth.domain.cms.entity.Board
import org.example.auth.infrastructure.util.modelMapTo
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
    private val boardDao: BoardJpaDao,
) : BoardService {
    override fun getBoardList(): List<BoardDto> {
        return boardDao.findAll().map { it.modelMapTo<BoardDto>() }.toList()
    }

    override fun saveBoard(board: BoardDto) {
        // null일때만 저장한다.
        boardDao.findById(board.boardType).ifPresentOrElse({}, {
            boardDao.save(board.modelMapTo())
        })
    }

    override fun deleteBoard(boardType: String) {
        boardDao.delete(Board(boardType = boardType))
    }

    override fun getBoard(boardName: String): BoardDto {
        val board = boardDao.findByBoardName(boardName) ?: throw Exception()
        return board.modelMapTo()
    }
}
