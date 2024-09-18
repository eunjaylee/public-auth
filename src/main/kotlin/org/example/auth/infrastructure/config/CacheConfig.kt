package org.example.auth.infrastructure.config

import org.example.auth.application.auth.service.KeyValueStore
import org.example.auth.infrastructure.cache.DbKeyValueStoreImpl
import org.example.auth.infrastructure.cache.RedisKeyValueStoreImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import javax.sql.DataSource

@Configuration
class CacheConfig {
    @ConditionalOnProperty(name = ["spring.redis.enable"], havingValue = "true", matchIfMissing = true)
    @Bean
    fun redisKeyValueStore(redisTemplate: RedisTemplate<String, Any>): KeyValueStore {
        return RedisKeyValueStoreImpl(redisTemplate)
    }

    @ConditionalOnMissingBean()
    @Bean
    fun dbKeyValueStore(dataSource: DataSource): KeyValueStore {
        return DbKeyValueStoreImpl().also {
            it.dataSource = dataSource
        }
    }
}
