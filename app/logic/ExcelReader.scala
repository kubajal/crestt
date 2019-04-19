package logic

import java.io.File

import org.apache.poi.ss.usermodel.WorkbookFactory
import scala.collection.JavaConverters._

import scala.io.Source

class ExcelReader(path: String) {
  def getLines: Iterator[String] = {
    val workbook = WorkbookFactory.create(new File(path))
    val sheet = workbook.getSheetAt(0)
    sheet.rowIterator().asScala.map(e => e.toString) //todo: fix this

  }
}
