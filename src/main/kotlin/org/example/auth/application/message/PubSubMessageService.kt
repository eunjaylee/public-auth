package org.example.auth.application.message

interface PubSubMessageService {
    fun findAllTopic(): Set<String>

    fun createTopic(topicId: String)

    fun removeTopic(topicId: String)

    fun pushMessage(
        topicId: String,
        message: String,
    )

    fun getKey(key: String): String
}
