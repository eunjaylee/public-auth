package org.example.auth.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients //(basePackages = ["org.example.auth.infrastructure.external"])
class FeignConfig {
}