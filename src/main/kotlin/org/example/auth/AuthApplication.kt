package org.example.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync

// @ComponentScan(basePackages = ["org.example"])
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync
@EnableFeignClients
class AuthApplication

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}
