package org.example.auth.config

// @Configuration
// class PostgreTestConfig {
//
//    @Bean
//    fun dataSource(): DataSource {
//        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:13.3").apply {
//            withDatabaseName("test")
//            withUsername("test")
//            withPassword("test")
//            start()
//        }
//
//        return DriverManagerDataSource().apply {
//            setDriverClassName(postgresContainer.driverClassName)
//            url = postgresContainer.jdbcUrl
//            username = postgresContainer.username
//            password = postgresContainer.password
//        }
//    }
//
// }
