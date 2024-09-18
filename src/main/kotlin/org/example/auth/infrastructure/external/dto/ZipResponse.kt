package org.example.auth.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ZipResponse(
    var results: ZipResult = ZipResult(),
)

data class ZipResult(
    var common: ZipCommon = ZipCommon(),
    var juso: List<ZipJuso> = emptyList(),
)

data class ZipCommon(
    var errorMessage: String = "",
    var errorCode: String = "",
    var countPerPage: String = "",
    var currentPage: String = "",
    var totalCount: String = "",
)

data class ZipJuso(
    var roadAddrPart1: String? = null,
    var roadAddrPart2: String? = null,
    var jibunAddr: String? = null,
    var zipNo: String? = null,
)
