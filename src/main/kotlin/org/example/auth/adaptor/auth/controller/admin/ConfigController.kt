package org.example.auth.adaptor.auth.controller.admin

import io.swagger.v3.oas.annotations.Operation
import org.example.auth.adaptor.dto.BooleanResponse
import org.example.auth.application.auth.service.OauthConfigService
import org.example.auth.domain.auth.entity.auth.OAuth2Client
import org.springframework.web.bind.annotation.*

@RequestMapping("/admin/api/v1/user/config")
class ConfigController(
    private val oauthConfigService: OauthConfigService,
) {
    /**
     * Oauth2 Client 정보를 등록한다.
     */
    @Operation(summary = "Oauth client 정보등록")
    @PostMapping("/oauth2")
    fun registOauth2Provider(
        @RequestBody oAuth2Client: OAuth2Client,
    ): BooleanResponse {
        oauthConfigService.registeOauthProvider(oAuth2Client)
        return BooleanResponse("add Oauth2Provider", true)
    }

    /**
     * Oauth2 Client  목록을 조회 한다.
     */
    @Operation(summary = "Oauth client 정보조회")
    @GetMapping("/oauth2")
    fun listOauth2Provider(): List<OAuth2Client> {
        return oauthConfigService.listOauthProvider()
    }

    /**
     * Oauth2 Client  정보를 삭제 한다.
     */
    @Operation(summary = "Oauth client 정보삭제")
    @DeleteMapping("/oauth2/{id}")
    fun delOauth2Provider(
        @PathVariable id: Long,
    ): BooleanResponse {
        oauthConfigService.delOauthProvider(id)
        return BooleanResponse("remove Oauth2Provider", true)
    }
}
