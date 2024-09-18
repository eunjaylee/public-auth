package org.example.auth.config

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@Testcontainers
class MysqlTestContainer  : ApplicationContextInitializer<ConfigurableApplicationContext> {

    //    companion object {
//        @Container
//        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8")
//
//        @JvmStatic
//        @DynamicPropertySource
//        fun overrideProps(registry: DynamicPropertyRegistry) {
//            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
//            registry.add("spring.datasource.username", mysqlContainer::getUsername)
//            registry.add("spring.datasource.password", mysqlContainer::getPassword)
//        }
//    }
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        // MySQLContainer 초기화
        val mysqlContainer = MySQLContainer<Nothing>("mysql:8")

        mysqlContainer.start()

        // 스프링 환경에 MySQLContainer 정보 등록
        val environment = applicationContext.environment
        val mutablePropertySource = environment.propertySources
        mutablePropertySource.addFirst(org.springframework.core.env.MapPropertySource(
            "testcontainers",
            mapOf(
                "spring.datasource.url" to mysqlContainer.jdbcUrl,
                "spring.datasource.username" to mysqlContainer.username,
                "spring.datasource.password" to mysqlContainer.password
            )
        ))
    }
}