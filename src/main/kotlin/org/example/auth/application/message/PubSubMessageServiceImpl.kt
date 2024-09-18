package org.example.auth.application.message

// @Service
// class PubSubMessageServiceImpl (
//    val redisMessageListener: RedisMessageListenerContainer,
//    val redisPublisher: RedisPublisher,
// //    val redisSubscriber: RedisSubscriber,
//    var redisTemplate: RedisTemplate<String, Any>
// ) : PubSubMessageService {
//
//    private var channels: MutableMap<String, ChannelTopic> = HashMap()
//
//    @PostConstruct
//    fun init() {
//        // fundId별로??
//        redisMessageListener.addMessageListener(redisSubscriber, ChannelTopic("trade"))
//    }
//
//    // 유효한 Topic 리스트 반환
//    override fun findAllTopic(): Set<String> {
//        return channels.keys
//    }
//
//    override fun createTopic(topicId: String) {
//        val channel = ChannelTopic(topicId)
//        redisMessageListener.addMessageListener(redisSubscriber, channel)
//        channels[topicId] = channel
//    }
//
//    override fun pushMessage(topicId: String, message: String) {
//        redisPublisher.publish(channels[topicId]!!, message)
//    }
//
//
//    // Topic 삭제 후 Listener 해제, Topic Map에서 삭제
//    override fun removeTopic(topicId: String) {
//        val channel = channels[topicId]!!
//        redisMessageListener.removeMessageListener(redisSubscriber, channel)
//        channels.remove(topicId)
//    }
//
//    override fun getKey(key: String): String {
//        val vop = redisTemplate.opsForValue()
//        return vop[key] as String
//    }
// }
