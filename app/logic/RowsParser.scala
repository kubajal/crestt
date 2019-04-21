package logic
import org.apache.poi.ss.usermodel.{Cell, Row}

import scala.util.{Failure, Try}
import scala.util.parsing.combinator._
import scala.collection.JavaConverters._

class RowsParser {

  private val columnSeparator = ";"

  /**
    * Parse string in CSV format to ParsedRow (column separator: ';').
    * @param cellNumber number of processed cell
    * @param csv CSV row
    * @return ParsedRow
    */

  def parse(cellNumber: Int, csv: String): ParsedRow ={
    if(csv.startsWith(columnSeparator))
      parse(cellNumber + 1, csv.tail)
    else {

      val split = csv.split(columnSeparator)

      // the remaining string has to start with a string value
      // and end with the ID of the row
      if(split.size != 2)
        new Exception("Invalid format of CSV")
      ParsedRow(split.head, cellNumber, split.last.toInt)
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

  /**
    * Create ParsedRow from Apache.POI.Row
    * @param row Apache.POI.Row
    * @return ParsedRow
    */

  def parse(row: Row) : Try[ParsedRow] = {

    Try({
      val csv = row.cellIterator()
        .asScala
        .map(e => {
          e.getCellType match {
            case Cell.CELL_TYPE_NUMERIC => // getStringCellValue on NUMERIC throws exception, so we have to cast it here to STRING
              e.setCellType(Cell.CELL_TYPE_STRING)
            case Cell.CELL_TYPE_STRING => // ok (getStringCellValue doesnt throw exception), continue
            case Cell.CELL_TYPE_BLANK => // ok (getStringCellValue doesnt throw exception), continue
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
