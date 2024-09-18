package org.example.auth.config

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser(username = "user", password = "password")
    @Throws(Exception::class)
    fun testSuccessHandler() {
        mockMvc.perform(
            post("/login")
                .param("username", "test")
                .param("password", "test"),
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/default-url"))
    }
}
