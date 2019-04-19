package utils

import org.apache.poi.xssf.usermodel.{XSSFRow, XSSFWorkbook}

class POIRowCreator{
  private var rowsNum = 0
  private val wb = new XSSFWorkbook
  private val sheet = wb.createSheet("dummy")
  def createRow(cells: List[String]): XSSFRow = {
    var row = sheet.createRow(rowsNum)
    var i = 0
    for(i <- cells.indices)
    {
      val cell = row.createCell(i)
      cell.setCellValue(cells(i))
    }
    rowsNum = rowsNum + 1
    row
  }
}