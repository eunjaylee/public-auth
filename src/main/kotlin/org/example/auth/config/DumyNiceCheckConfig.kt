package org.example.auth.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DumyNiceCheckConfig {
    @ConditionalOnMissingBean
    @Bean
    fun niceCheck(): NiceCheck {
        println("=============")
        return NiceCheck(HashMap<String, String>())
    }
}
