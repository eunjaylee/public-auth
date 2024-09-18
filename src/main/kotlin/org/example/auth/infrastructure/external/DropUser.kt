package org.example.auth.infrastructure.external

import org.example.auth.domain.auth.entity.UserPersonalInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "board", url = "http://localhost:3001")
interface DropUser {
    @PostMapping("/userInfo", produces = ["application/json"])
    fun save(
        @RequestBody userPersonalInfo: UserPersonalInfo,
    )

    @GetMapping("/userInfo", produces = ["application/json"])
    fun findUser()
}
