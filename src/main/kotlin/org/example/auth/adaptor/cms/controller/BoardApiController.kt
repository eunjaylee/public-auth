package org.example.auth.adaptor.cms.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.example.auth.adaptor.cms.dto.BoardDto
import org.example.auth.application.cms.service.BoardService
import org.springframework.web.bind.annotation.*

@Tag(description = "게시판 관련", name = "Board")
@RestController
@RequestMapping("/api/v1/board")
class BoardApiController(
    val boardService: BoardService,
) {
    @GetMapping("/list")
    fun boardList(): List<BoardDto> {
        return boardService.getBoardList()
    }

    @PostMapping("/boards")
    fun saveBoard(
        @RequestBody board: BoardDto,
    ): BoardDto {
        boardService.saveBoard(board)
        return board
    }

    @GetMapping("/boards/{boardName}")
    fun getBoard(
        @PathVariable boardName: String,
    ): BoardDto {
        return boardService.getBoard(boardName)
    }
}
