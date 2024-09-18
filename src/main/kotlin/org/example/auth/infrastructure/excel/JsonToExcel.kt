package org.example.auth.infrastructure.excel

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import java.io.IOException

class JsonToExcel {
    fun makeStyle(
        workbook: Workbook,
        style: CellStyle,
    ) {
        val font = workbook.createFont()
        font.bold = true
        style.setFont(font)

        style.fillForegroundColor = IndexedColors.YELLOW.getIndex()
        style.fillPattern = FillPatternType.SOLID_FOREGROUND

        style.borderTop = BorderStyle.THIN
        style.borderBottom = BorderStyle.THIN
        style.borderLeft = BorderStyle.THIN
        style.borderRight = BorderStyle.THIN
    }

    fun test(json: String) {
        // JSON 데이터 (문자열 형식)
        val jsonData = """
                      [
                       {"name": "John Doe",   "age": 30,   "city": "New York"}, 
                       {"name": "Jane Smith", "age": 25,   "city": "Los Angeles"},
                       {"name": "Mike Johnson", "age": 35, "city": "Chicago"}
                      ]
                    """

        // Excel 워크북 생성
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("Sheet1")

        try {
            // JSON 문자열을 JsonNode 객체로 변환
            val objectMapper: ObjectMapper = ObjectMapper()
            val rootNode: JsonNode = objectMapper.readTree(jsonData)

            // 첫 번째 행(헤더) 생성
            val headerRow: Row = sheet.createRow(0)
            var fieldNames = rootNode.get(0).fieldNames()
            var headerColumnIndex = 0

            val style = workbook.createCellStyle()

            makeStyle(workbook, style)

            while (fieldNames.hasNext()) {
                var fieldName = fieldNames.next()
                var cell: Cell = headerRow.createCell(headerColumnIndex++)
                cell.setCellValue(fieldName)

                cell.cellStyle = style
            }

            // JSON 데이터의 각 객체를 Excel의 행으로 변환
            var rowIndex = 1

            rootNode.forEach {
                var row = sheet.createRow(rowIndex++)
                var columnIndex = 0
                var fields = it.fieldNames()
                while (fields.hasNext()) {
                    var field = fields.next()
                    var cell: Cell = row.createCell(columnIndex++)
                    var valueNode: JsonNode = it.get(field)

                    if (valueNode.isTextual) {
                        cell.setCellValue(valueNode.asText())
                    } else if (valueNode.isInt || valueNode.isDouble) {
                        cell.setCellValue(valueNode.asDouble())
                    }
                    cell.cellStyle = style
                }
            }

            // 열 너비 자동 조정
            for (i in 0..2) {
                sheet.autoSizeColumn(i)
            }

            FileOutputStream("output.xlsx").use { fileOut ->
                workbook.write(fileOut)
            }

            // 워크북 종료
            workbook.close()
            println("Excel 파일이 성공적으로 생성되었습니다.")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
