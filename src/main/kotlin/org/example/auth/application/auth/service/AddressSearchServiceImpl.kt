package org.example.auth.application.auth.service

import org.example.auth.domain.auth.KorAddress
import org.example.auth.domain.auth.ZipCode
import org.example.auth.infrastructure.external.AddressSearchApi
import org.example.auth.infrastructure.external.ZipCodeSearchApi
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service

@Service
class AddressSearchServiceImpl(
    private val addressSearchApi: AddressSearchApi,
    private val zipCodeSearchApi: ZipCodeSearchApi,
) : AddressSearchService {
    override fun addressSearch(
        query: String,
        pageable: Pageable,
    ): Slice<KorAddress> {
        val addressResponse = addressSearchApi.addressSearch(query, pageable.pageNumber, pageable.pageSize)
        return SliceImpl(
            addressResponse.documents.map {
                KorAddress(it.address_name, it.address_type, it.road_address, it.x, it.y)
            },
            pageable,
            !addressResponse.meta.is_end,
        )
    }

    override fun zipcodeSearch(
        query: String,
        pageable: Pageable,
    ): Slice<ZipCode> {
        val zipCodeResponse = zipCodeSearchApi.searchZipCode(query, pageable.pageNumber, pageable.pageSize)

        return SliceImpl(
            zipCodeResponse.results.juso.map { ZipCode(it.roadAddrPart1, it.roadAddrPart2, it.jibunAddr, it.zipNo) },
            pageable,
            zipCodeResponse.results.common.totalCount.toInt() / pageable.pageSize > pageable.pageNumber,
        )
    }
}
