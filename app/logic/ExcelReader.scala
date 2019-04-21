package logic

import java.io.File

import javax.inject.Singleton
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}

import scala.collection.JavaConverters._
import scala.io.Source

@Singleton
class ExcelReader(private val path: String) {

  def getPoiRows: Iterator[Row] = {

    val file = new File(path)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheetAt(0)
    sheet.rowIterator().asScala
  }
}
