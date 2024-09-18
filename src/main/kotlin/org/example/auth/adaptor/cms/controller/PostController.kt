package org.example.auth.adaptor.cms.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import mu.KLogging
import org.example.auth.adaptor.cms.dto.PostsDto
import org.example.auth.adaptor.cms.dto.PostsListReq
import org.example.auth.adaptor.cms.dto.PostsParamReq
import org.example.auth.application.cms.service.PostService
import org.example.auth.domain.auth.Account
import org.example.auth.domain.cms.entity.Posts
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@Tag(description = "게시판 관련", name = "Posts")
@RestController
@RequestMapping("/api/v1/")
class PostController(
    val postService: PostService,
) {
    companion object : KLogging()

    @ModelAttribute
    fun postsParamReq(): PostsParamReq {
        val param: PostsParamReq = PostsParamReq()
        val account = SecurityContextHolder.getContext().authentication.principal as Account
        param.userSeq = account.userSeq
        return param
    }

    @Operation(summary = "게시글 목록")
    @GetMapping("/{boardType}/postList")
    fun getPostsList(
        param: PostsParamReq,
        @PageableDefault(page = 0, size = 20) pageable: Pageable,
    ): Page<PostsListReq> {
        var postList = postService.getPostsList(param, pageable)
        return postList
    }

    @Operation(summary = "게시글 상세")
    @GetMapping("/{boardType}/post/{postSeq}")
    fun getPost(
        @PathVariable boardType: String,
        param: PostsParamReq,
        @PathVariable postSeq: Long,
    ): Posts? {
        // 권한체크가...
        var param = PostsParamReq(boardType = boardType, postSeq = postSeq)
        return postService.getPost(param)
    }

    @Operation(summary = "게시글 저장")
    @PostMapping("/{boardType}/post")
    fun savePost(
        @PathVariable boardType: String,
        @RequestBody @Valid posts: PostsDto,
    ): PostsDto {
        return postService.savePost(posts)
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{boardType}/post/{postSeq}")
    fun deletePost(
        @PathVariable boardType: String,
        @PathVariable postSeq: Int,
    ) {
        // 관리자이거나, 자기가 작성했거나??
    }

    // TODO 게시글 순서변경
    @Operation(summary = "게시글 순서변경")
    @PutMapping("/{boardType}/post/order")
    fun reOrderPOsts(
        @PathVariable boardType: String,
    ) {
        TODO("Not yet implemented")
    }

    @Operation(summary = "좋아요/싫어요")
    @PutMapping("/{boardType}/post/{postSeq}")
    fun reactionPost(
        @PathVariable boardType: String,
        @PathVariable postSeq: Int,
    ) {
        TODO("Not yet implemented")
    }
}
