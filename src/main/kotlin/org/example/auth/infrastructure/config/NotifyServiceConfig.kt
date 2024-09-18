package org.example.auth.infrastructure.config

import org.example.auth.application.auth.service.NotifyService
import org.example.auth.infrastructure.notify.GmailNotifyServiceImpl
import org.example.auth.infrastructure.notify.NotiServiceType
import org.example.auth.infrastructure.util.toStringByReflection
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
@EnableConfigurationProperties(GmailConfigProperty::class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
class NotifyServiceConfig {
    @ConditionalOnMissingBean
    @Bean
    fun notifyService(properties: GmailConfigProperty): NotifyService {
        println("create bean : NotifyService")
        properties.props.map { println("${it.key} : ${it.value}") }
        println(properties.toStringByReflection())
        println("================== NotifyService")
        return GmailNotifyServiceImpl(properties, NotiServiceType.EMAIL)
    }
}
