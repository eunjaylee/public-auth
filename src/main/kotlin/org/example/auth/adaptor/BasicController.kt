package org.example.auth.adaptor

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(description = "홈화면 관련", name = "home")
@RequestMapping("/api/v1")
@RestController
class BasicController {
    @Operation(summary = "메인화면")
    @GetMapping("/index")
    fun index(): Any {
        return SecurityContextHolder.getContext().authentication.principal
    }
}
