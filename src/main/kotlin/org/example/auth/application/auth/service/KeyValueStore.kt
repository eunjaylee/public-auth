package org.example.auth.application.auth.service

interface KeyValueStore {
    fun getKey(key: String): String?

    fun setKey(
        key: String,
        value: String,
        ttl: Long,
    )

    fun removeKey(key: String)
}
