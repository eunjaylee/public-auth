package org.example.auth.infrastructure.cache

import org.example.auth.application.auth.service.KeyValueStore
import org.example.auth.config.MysqlTestContainer
import org.example.auth.infrastructure.cipher.AesCipherImpl
import org.example.auth.infrastructure.config.CacheConfig

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@Import(CacheConfig::class, AesCipherImpl::class)
//@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트 하기 위한 설정
//@Rollback(false)
@ContextConfiguration(initializers = [MysqlTestContainer::class])
@ActiveProfiles("test")
@DisplayName("캐시 테스트")
class DbKeyValueStoreImplTest {
    @Autowired
    lateinit var keyValueStore: KeyValueStore

    @Test
    fun getKey() {
        val test = keyValueStore.getKey("test")

        println(test)
    }

    @Test
    fun setKey() {
        val ttl = 1000L
        keyValueStore.setKey("test", "test1", ttl)

        println(keyValueStore.getKey("test"))

        Thread.sleep(ttl)

        println(keyValueStore.getKey("test"))
    }

    @Test
    fun removeKey() {
        keyValueStore.removeKey("test")
    }
}
