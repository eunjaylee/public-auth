package org.example.auth.application.message // package org.example.auth.application.cms.service
//
// import mu.KLogging
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import org.springframework.data.redis.connection.Message
// import org.springframework.data.redis.connection.MessageListener
// import org.springframework.data.redis.connection.RedisConnectionFactory
// import org.springframework.data.redis.core.RedisTemplate
// import org.springframework.data.redis.listener.ChannelTopic
// import org.springframework.data.redis.listener.RedisMessageListenerContainer
// import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
// import org.springframework.stereotype.Service
// import org.springframework.web.socket.TextMessage
//
// //@Service
// class RedisSubscriber(val redisTemplate: RedisTemplate<String, Any>,
//                      val webSocketHandler : WebSocketHandler
// ) : MessageListener {
//    companion object : KLogging()
//
//    override fun onMessage(message: Message, pattern: ByteArray?) {
//        try {
//            var body : String? = redisTemplate.getStringSerializer().deserialize(message.getBody())
//            webSocketHandler.broadCastMessage(TextMessage(body!!))
//            logger.info("order - Message : {}", redisTemplate.stringSerializer.deserialize(message.body))
//            logger.debug("pattern : {} ", pattern)
//        } catch (e: Exception) {
//            logger.error(e.message)
//        }
//    }
// }
//
