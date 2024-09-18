package org.example.auth.infrastructure.external

import org.example.auth.infrastructure.external.dto.AddressResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "AddressSearch", url = "\${testAuth.kakaoJusoApi.host}")
interface AddressSearchApi {
    @GetMapping(
        value = ["/v2/local/search/address.json?analyze_type=similar"],
        produces = ["application/json"],
    )
    fun addressSearch(
        @RequestParam(name = "query") query: String,
        @RequestParam(name = "page") page: Int = 1,
        @RequestParam(name = "size") size: Int = 30,
        @RequestHeader(
            value = "Authorization",
            required = true,
        ) authorizationHeader: String = "\${testAuth.kakaoJusoApi.host}",
    ): AddressResponse
}
