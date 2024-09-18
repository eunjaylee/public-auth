package org.example.auth.application.auth.service

import org.example.auth.domain.auth.KorAddress
import org.example.auth.domain.auth.ZipCode
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface AddressSearchService {
    fun addressSearch(
        query: String,
        pageable: Pageable,
    ): Slice<KorAddress>

    fun zipcodeSearch(
        query: String,
        pageable: Pageable,
    ): Slice<ZipCode>
}
