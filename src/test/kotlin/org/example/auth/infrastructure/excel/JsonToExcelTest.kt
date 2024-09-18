package org.example.auth.infrastructure.excel

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JsonToExcelTest {
    @Test
    fun make() {
        val jsonToExcel = JsonToExcel()

        jsonToExcel.test("")
    }
}
