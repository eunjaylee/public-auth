package org.example.auth.infrastructure.cache

import org.example.auth.application.auth.service.KeyValueStore
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class DbKeyValueStoreImpl : JdbcDaoSupport(), KeyValueStore {
    companion object {
        const val INSERT_CACHE_SQL: String = "insert into cache_table (cache_key, cache_value, expired) values (?,?,?)"
        const val DELETE_CACHE_SQL: String = "delete from cache_table where cache_key = ?"
        const val GET_VALUE_SQL: String = "select cache_value from cache_table where cache_key = ? and expired > now() "
    }

    override fun getKey(key: String): String {
        // JdbcUserDetailsManager
        var values =
            jdbcTemplate.query<String>(
                GET_VALUE_SQL,
                RowMapper<String> { rs: ResultSet, rowNum: Int -> this.mapToValue(rs, rowNum) },
                key,
            )

        return if (values.size == 0) {
            ""
        } else {
            values[0]
        }
    }

    @Throws(SQLException::class)
    private fun mapToValue(
        rs: ResultSet,
        rowNum: Int,
    ): String {
        val value = rs.getString(1)
        return value
    }

    override fun setKey(
        key: String,
        value: String,
        ttl: Long,
    ) {
        jdbcTemplate.update(Companion.INSERT_CACHE_SQL) { ps: PreparedStatement ->
            run {
                ps.setString(1, key)
                ps.setString(2, value)
                ps.setTimestamp(3, java.sql.Timestamp(System.currentTimeMillis() + ttl))
            }
        }
    }

    override fun removeKey(key: String) {
        jdbcTemplate.update(DELETE_CACHE_SQL) { ps: PreparedStatement ->
            run {
                ps.setString(1, key)
            }
        }
    }
}
