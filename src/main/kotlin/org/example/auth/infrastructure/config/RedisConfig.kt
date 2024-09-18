package org.example.auth.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Component

// @ConfigurationProperties("spring.data.redis")
@Component
class RedisConfig {
    @Bean
    fun RedisMessageListener(connectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        var container = RedisMessageListenerContainer()
        container.connectionFactory = connectionFactory
        return container
    }

    @Bean
    fun redisTemplateForObject(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any>? {
        var redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = connectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(String::class.java)
        return redisTemplate
    }
}
