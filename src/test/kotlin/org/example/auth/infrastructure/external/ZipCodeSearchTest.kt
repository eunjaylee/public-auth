package org.example.auth.infrastructure.external

import org.example.auth.infrastructure.external.dto.ZipResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class ZipCodeSearchTest {

    @Autowired
    lateinit var zipCodeSearchApi : ZipCodeSearchApi


    @Test
    fun zipcodeSearch() {
        val zipResponse : ZipResponse = zipCodeSearchApi.searchZipCode("면목동")
        Assertions.assertEquals(30, zipResponse.results.juso.count())
    }
}