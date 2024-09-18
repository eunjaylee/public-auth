package org.example.auth.application.cms.service

// import com.googlecode.jmapper.JMapper

import jakarta.transaction.Transactional
import org.example.auth.adaptor.cms.dto.PostsDto
import org.example.auth.adaptor.cms.dto.PostsListReq
import org.example.auth.adaptor.cms.dto.PostsParamReq
import org.example.auth.domain.auth.Account
import org.example.auth.domain.cms.dao.*
import org.example.auth.domain.cms.entity.*
import org.example.auth.infrastructure.util.modelMapTo
// import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
@Transactional
class PostServiceImpl(
    val postsDao: PostsDao,
    val attachFileDao: PostsAttachFileJpaDao,
    val postReactionService: PostReactionService,
    val pointTransactionJpaDao: PointTransactionJpaDao,
) : PostService {
    override fun getPostsList(
        postsParamReq: PostsParamReq,
        pageable: Pageable,
    ): Page<PostsListReq> {
        return postsDao.getPostList(postsParamReq, pageable)
    }

    override fun getPost(postsParamReq: PostsParamReq): Posts? {
        var result: PostsDto = PostsDto()
//        postsDao.getPostDetail(postsParamReq)?.let {
//            result = modelMapper.map(it, PostsRes::class.java )
//        }

        // admin혹은 작성자이면 포인트 차감없이 읽을 수 있음
        // post를 가져오기 전에 차감여부 확인 및 차감을 처리하고 응답으로 에러를 줘야함..
        // 읽은 글에 대해서는 재 읽기 권한을 준다고 하면? transaction을 한번더 검증해야함..

        val posts = postsDao.getPostDetail(postsParamReq) ?: throw Exception("게시글이 존재하지 않습니다.")

        // 읽음 == 포인트 차감 같은거.. 차감 포인트만 계산하자..

        // 포인트 처리
        pointTransactionJpaDao.findByUserSeq(postsParamReq.userSeq)

        // 읽음 처리
        postReactionService.readAction(PostReadCheck(pk = PostReadPk(postSeq = postsParamReq.postSeq!!, userSeq = postsParamReq.userSeq)))

        return posts
    }

    override fun getIsOwnPost(params: PostsParamReq): Posts? {
        var result: PostsDto = PostsDto()
//        postsDao.getPostDetail(postsParamReq)?.let {
//            result = modelMapper.map(it, PostsRes::class.java )
//        }

        // admin혹은 작성자이면 포인트 차감없이 읽을 수 있음
        // post를 가져오기 전에 차감여부 확인 및 차감을 처리하고 응답으로 에러를 줘야함..
        // 읽은 글에 대해서는 재 읽기 권한을 준다고 하면? transaction을 한번더 검증해야함..

        val posts = postsDao.getPostDetail(params) ?: throw Exception("게시글이 존재하지 않습니다.")

        return posts
    }

    override fun deletePost(params: PostsParamReq) {
        // 삭제할때 조건이 뭘까?? --> 작성자가 나이고 ...
        val posts = postsDao.findById(params.postSeq) ?: throw Exception("게시글이 존재하지 않습니다.")

        if (posts.get().userSeq != (SecurityContextHolder.getContext().authentication.principal as Account).userSeq) {
            throw Exception("")
        }

        postsDao.deleteById(params.postSeq)
    }

    override fun savePost(postsDto: PostsDto): PostsDto {
        println("=================1")

        val posts = postsDto.modelMapTo<Posts>()

        // 기간이 있으면 기간 설정
        posts.postsPeriod?.let {
            it.posts = posts
        }

        // meta가 있으면 meta설정
        posts.metaList?.let {
            it.forEach { meta ->
                meta.posts = posts
            }
        }

        println("=================3")
        postsDao.save(posts)

        println("=================4")
        // 파일 저장
        posts.attachSeqList?.let { attach ->
            attach.forEach {
                attachFileDao.findById(it).get().postSeq = posts.postSeq
            }
        }

        return posts.modelMapTo<PostsDto>()
    }
}
