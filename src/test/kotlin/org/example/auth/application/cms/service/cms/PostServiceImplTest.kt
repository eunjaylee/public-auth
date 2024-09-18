package org.example.auth.application.cms.service.cms

import org.example.auth.adaptor.cms.dto.PostsDto
import org.example.auth.application.cms.service.PostReactionService
import org.example.auth.application.cms.service.PostReactionServiceImpl
import org.example.auth.application.cms.service.PostService
import org.example.auth.application.cms.service.PostServiceImpl
import org.example.auth.config.MysqlTestContainer
import org.example.auth.domain.cms.dao.PostsDao
import org.example.auth.domain.cms.dao.PostsDaoImpl
import org.example.auth.domain.cms.entity.Posts
import org.example.auth.infrastructure.cipher.AesCipherImpl
import org.example.auth.infrastructure.config.QuerydslConfig
import org.example.auth.infrastructure.file.LocalStorageFileHandler
import org.example.auth.infrastructure.util.modelMapTo
import org.example.auth.infrastructure.util.toStringByReflection
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@Import(
    PostServiceImpl::class,
    PostsDaoImpl::class,
    PostReactionServiceImpl::class,
    QuerydslConfig::class,
    LocalStorageFileHandler::class,
    AesCipherImpl::class,
)
//@ContextConfiguration(initializers = [MysqlTestContainer::class])
//@Rollback(false)
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트
@ActiveProfiles("test")
// 하기 위한 설정
@DisplayName("게시판 테스트")
class PostServiceImplTest {
    @Autowired
    lateinit var postsDao: PostsDao

    @Autowired
    lateinit var postReactionService: PostReactionService

    @Autowired
    lateinit var postService: PostService

    @Test
    fun getPostsList() {
    }

    @Test
    fun getPost() {
    }

    @Test
    fun getIsOwnPost() {
    }

    @Test
    fun deletePost() {
    }

    @Test
    fun savePost() {
        val postsDto =
            PostsDto().apply {
                this.postTitle = "제목"
                this.postContent = "내용"
                this.userSeq = 8
                this.boardType = "FAQ"
            }

        println(postsDto.toStringByReflection())

        val posts = postsDto.modelMapTo<Posts>()

        println(posts.toStringByReflection())
    }
}
