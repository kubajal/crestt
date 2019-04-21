package logic
import org.apache.poi.ss.usermodel.{Cell, Row}

import scala.util.{Failure, Try}
import scala.util.parsing.combinator._
import scala.collection.JavaConverters._

class RowsParser {

  private val columnSeparator = ";"

  def parse(level: Int, csv: String): ParsedRow ={
    if(csv.startsWith(columnSeparator))
      parse(level + 1, csv.tail)
    else {

      val split = csv.split(columnSeparator)

      // the remaining string has to start with a string value
      // and end with the ID of the row
      if(split.size != 2)
        new Exception("Invalid format")
      ParsedRow(split.head, level, split.last.toInt)
    }
  }

  /**
    * Helper function that sanitizes null strings.
    * @param s string that can be null
    * @return null replaced with ""
    */
  private def checkIfNull(s: String): String = {
    if(s == null) "" else s
  }

  def parse(row: Row) : Try[ParsedRow] = {

    Try({
      val csv = row.cellIterator()
        .asScala
        .map(e => {
          e.getCellType match {
            case Cell.CELL_TYPE_NUMERIC =>
              e.setCellType(Cell.CELL_TYPE_STRING)
            case Cell.CELL_TYPE_STRING => // ok, continue
            case Cell.CELL_TYPE_BLANK => // ok, continue
            case _ => throw new Exception("Unsupported cell type")
          }
          e.getStringCellValue
        })
        .map(e => checkIfNull(e))
        .reduce((x, y) => x + columnSeparator + y)

      parse(0, csv)
    })
  }
}
