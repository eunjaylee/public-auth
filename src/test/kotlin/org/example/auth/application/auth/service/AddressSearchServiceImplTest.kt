package org.example.auth.application.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.auth.config.FeignConfig
import org.example.auth.infrastructure.external.ZipCodeSearchApi
import org.example.auth.infrastructure.external.dto.ZipResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Import

//@SpringBootTest
@WebMvcTest
@Import(ZipCodeSearchApi::class, ObjectMapper::class)
@ImportAutoConfiguration(classes = [FeignConfig::class, FeignAutoConfiguration::class, HttpMessageConvertersAutoConfiguration::class])
class AddressSearchServiceImplTest {

    @Autowired
    lateinit var zipCodeSearchApi : ZipCodeSearchApi


    @Test
    fun addressSearch() {
    }

    @Test
    fun zipcodeSearch() {
        val zipResponse : ZipResponse = zipCodeSearchApi.searchZipCode("면목동")
        Assertions.assertEquals(30, zipResponse.results.juso.count())
    }
}