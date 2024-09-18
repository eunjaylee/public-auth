package org.example.auth.application.cms.service

import org.example.auth.adaptor.cms.dto.BoardDto
import org.springframework.security.access.prepost.PreAuthorize

interface BoardService {
    /**
     * 게시판 타입 조회
     */
    fun getBoardList(): List<BoardDto>

    /**
     * 게시판 타입 등록/수정
     */
    fun saveBoard(board: BoardDto)

    /**
     * 게시판 타입 삭제
     */
    fun deleteBoard(boardType: String)

    /**
     * 게시판 상세 조회
     */
    @PreAuthorize("hasPermission('', 'canWrite')")
    fun getBoard(boardName: String): BoardDto
}
