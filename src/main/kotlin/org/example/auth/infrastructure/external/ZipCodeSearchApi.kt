package org.example.auth.infrastructure.external

import org.example.auth.infrastructure.external.dto.ZipResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "zipAddress", url = "\${testAuth.jusoApi.host}")
interface ZipCodeSearchApi {
    @GetMapping(
        value = ["\${testAuth.jusoApi.api.ziplink}?confmKey=\${testAuth.jusoApi.confmKey}&currentPage={page}&countPerPage={size}&keyword={query}&resultType=json"],
        produces = ["application/json"],
    )
    fun searchZipCode(
        @RequestParam query: String,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 30,
    ): ZipResponse
}
