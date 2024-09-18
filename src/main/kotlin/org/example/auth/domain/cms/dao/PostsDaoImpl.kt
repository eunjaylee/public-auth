package org.example.auth.domain.cms.dao

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.example.auth.adaptor.cms.dto.PostsListReq
import org.example.auth.adaptor.cms.dto.PostsParamReq
import org.example.auth.domain.cms.entity.Posts
import org.example.auth.domain.cms.entity.QPosts
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class PostsDaoImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val postsJpaDao: PostsJpaDao,
) : PostsDao, PostsJpaDao by postsJpaDao {
    val table: QPosts = QPosts.posts

    private fun postCondition(postParam: PostsParamReq) =
        arrayOf(
            table.boardType.eq(postParam.boardType),
            // ID가 있거나 ==> 로그인 유져가 작성한 글만 봐야 할 경우
            postParam.userSeq.let {
                table.userSeq.eq(it)
            },
            // display여부가 있던가
            postParam.displayYn?.let {
                table.displayYn.eq(it)
            },
            // 특정 글 번호만 조회할 경우
            postParam.postSeq?.let {
                table.postSeq.eq(it)
            },
            // 검색어가 있으면 검색어로 조회
            postParam.searchKeyword?.let {
                table.postTitle.like(it)
            },
        )

    override fun getPostList(
        postParam: PostsParamReq,
        pageable: Pageable,
    ): Page<PostsListReq> {
        val condition = postCondition(postParam)

        val totalCount = jpaQueryFactory.select(table.count()).from(table).where(*condition).fetchOne()

        val result =
            jpaQueryFactory.select(
                Projections.constructor(
                    PostsListReq::class.java,
                    table.postSeq,
                    table.postTitle,
                    table.postStatus,
                    table.orderSeq,
                    table.displayYn,
                    table.createAt,
                    table.employSeq,
                    table.userSeq,
                ),
            ).from(table).where(*condition) // TODO : 스프레드 연산자로 인한 복사에 성능저하와 코드의 간결성 선택은?
                .orderBy(table.orderSeq.asc())
                .orderBy(table.postSeq.desc())
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

        return PageImpl(result, pageable, totalCount!!)
    }

    override fun getPostDetail(postParam: PostsParamReq): Posts? {
        val table = QPosts.posts

        val condition = postCondition(postParam)

        return jpaQueryFactory.selectFrom(table).where(*condition)
            .orderBy(table.orderSeq.asc())
            .orderBy(table.postSeq.desc())
            .fetchOne()
    }
}
