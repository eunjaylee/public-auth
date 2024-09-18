package org.example.auth.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
    @GetMapping("/login-test")
    fun test(): String {
        return "/login-test"
    }
}
