package org.example.auth.application.message

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic

// @Service
class RedisPublisher(private val redisTemplate: RedisTemplate<String, Any>) {
    fun publish(
        topic: ChannelTopic,
        message: String?,
    ) {
        redisTemplate.convertAndSend(topic.topic, message!!)
    }
}
