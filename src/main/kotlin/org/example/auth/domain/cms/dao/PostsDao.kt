package org.example.auth.domain.cms.dao

import org.example.auth.adaptor.cms.dto.PostsListReq
import org.example.auth.adaptor.cms.dto.PostsParamReq
import org.example.auth.domain.cms.entity.Posts
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface PostsDao : PostsJpaDao {
    fun getPostList(
        postParam: PostsParamReq,
        pageable: Pageable,
    ): Page<PostsListReq>

    fun getPostDetail(postParam: PostsParamReq): Posts?
}
