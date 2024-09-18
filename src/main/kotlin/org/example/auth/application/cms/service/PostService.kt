package org.example.auth.application.cms.service

import org.example.auth.adaptor.cms.dto.PostsDto
import org.example.auth.adaptor.cms.dto.PostsListReq
import org.example.auth.adaptor.cms.dto.PostsParamReq
import org.example.auth.domain.cms.entity.Posts
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize

interface PostService {
    /**
     * 게시글 리스트 조회
     */
//    @PreAuthorize("hasPermission(#params, 'canRead')")
    fun getPostsList(
        params: PostsParamReq,
        pageable: Pageable,
    ): Page<PostsListReq>

    /**
     * 게시글 상세 조회
     */
    @PreAuthorize("hasPermission(#params, 'canRead')")
    fun getPost(params: PostsParamReq): Posts?

    /**
     * 나의 게시글 조회
     */
    @PreAuthorize("hasPermission(#params, 'isOwner')")
    fun getIsOwnPost(params: PostsParamReq): Posts?

    /**
     * 게시글 삭제
     */
    @PreAuthorize("hasPermission(#params, 'isOwner')")
    fun deletePost(params: PostsParamReq)

    /**
     * 게시글 등록/수정
     */
    @PreAuthorize("hasPermission('', 'canWrite')")
    fun savePost(posts: PostsDto): PostsDto
}
