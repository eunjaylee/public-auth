package org.example.auth.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NiceCheckConfig {
    @ConditionalOnClass(name = ["NiceID.Check.CPClient"])
    @Bean
    fun niceCheck(): NiceCheck {
        println("============= : NiceID.Check.CPClient")
        val cpClientClass = Class.forName("NiceID.Check.CPClient")
        return NiceCheck(cpClientClass.getDeclaredConstructor().newInstance())
    }
}
