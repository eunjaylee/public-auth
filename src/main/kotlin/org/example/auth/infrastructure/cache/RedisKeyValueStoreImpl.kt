package org.example.auth.infrastructure.cache

import org.example.auth.application.auth.service.KeyValueStore
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

class RedisKeyValueStoreImpl(
    private val redisTemplate: RedisTemplate<String, Any>,
) : KeyValueStore {
    override fun getKey(key: String): String {
        return redisTemplate.opsForValue()[key].toString()
    }

    override fun setKey(
        key: String,
        value: String,
        ttl: Long,
    ) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl))
    }

    override fun removeKey(key: String) {
        redisTemplate.opsForValue().getAndDelete(key)
    }
}
