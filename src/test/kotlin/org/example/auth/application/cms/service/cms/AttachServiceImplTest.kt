package org.example.auth.application.cms.service.cms

// import org.example.auth.config.PostgreTestConfig
import org.example.auth.application.cms.service.AttachService
import org.example.auth.application.cms.service.AttachServiceImpl
import org.example.auth.config.MysqlTestContainer
import org.example.auth.domain.cms.dao.PostsAttachFileJpaDao
import org.example.auth.infrastructure.cipher.AesCipherImpl
import org.example.auth.infrastructure.config.QuerydslConfig
import org.example.auth.infrastructure.file.LocalStorageFileHandler
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@Import(AttachServiceImpl::class, LocalStorageFileHandler::class, AesCipherImpl::class, QuerydslConfig::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트
//@ContextConfiguration(initializers = [MysqlTestContainer::class])
//@Rollback(false)
// 하기 위한 설정
@DisplayName("File Upload 테스트")
class AttachServiceImplTest() {
    @Autowired
    lateinit var attachService: AttachService

    @Autowired
    lateinit var postsAttachFileJpaDao: PostsAttachFileJpaDao

    @Test
    fun `file업로드 테스트`() {
        val file =
            MockMultipartFile(
                "file", // 파라미터 이름
                "test.txt", // 파일 이름
                "text/plain", // 파일 타입
                "This is a test file".toByteArray(), // 파일 내용
            )
        attachService.uploadFiles(listOf(file))
    }

    @Test
    fun passwordTest() {
        val newPwd = "test123"

        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

        val encPwd = passwordEncoder.encode(newPwd)

        val encPwd2 = "{bcrypt}\$2a\$10\$iM5eVJHWLAQX28O4f0G8XOicwiCY.70EOdxa.FvuP.glHiP8APhle"

        println(passwordEncoder.matches(newPwd, encPwd))
        println(passwordEncoder.matches(newPwd, encPwd2))
        println(encPwd)
    }
}
