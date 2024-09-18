package org.example.auth.config

// import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
class OAuth2LoginTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    private val context: WebApplicationContext? = null

    @Test
    @Throws(Exception::class)
    fun oauth2LoginSuccessTest() {
        // MockMvc에 Security 설정 적용
        this.mockMvc =
            MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
                .build()

        val oauth2User: OAuth2User =
            DefaultOAuth2User(
                AuthorityUtils.createAuthorityList("SCOPE_message:read"),
                mapOf("user_name" to "foo_user"),
                "user_name",
            )

        mockMvc.perform(
            get("/api/v1/user/userInfo").with(
//                    oauth2Login()
//                        .attributes { attrs ->
//                                attrs.put("name", "Test User")
//                                attrs.put("email", "testuser@example.com")
//                        }
                oauth2Login().oauth2User(oauth2User)
                    .authorities(SimpleGrantedAuthority("SCOPE_message:read")),
            ),
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/")) // 리다이렉트 URL이 기대하는 것과 일치하는지 확인
    }
}
