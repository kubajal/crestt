package logic
import org.apache.poi.ss.usermodel.Row

import scala.util.{Failure, Try}
import scala.util.parsing.combinator._
import scala.collection.JavaConverters._

class RowsParser {

  val columnSeparator = ";"

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

  def parse(row: Row) : Try[ParsedRow] = {

    Try({
      val csv = row.cellIterator()
        .asScala
        .map(e => e.getStringCellValue)
        .reduce(_ + columnSeparator + _)

      parse(0, csv)
    })
  }
}
