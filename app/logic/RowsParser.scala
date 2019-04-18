package logic
import scala.util.{Failure, Try}
import scala.util.parsing.combinator._

class RowsParser {

  val columnSeparator = ";"

  def parse(level: Int, string: String): Try[Row] ={
    if(string.startsWith(columnSeparator))
      parse(level + 1, string.tail)
    else {

      val split = string.split(columnSeparator)

      // the remaining string has to start with a string value
      // and end with the ID of the row
      if(split.size != 2)
        Failure(new Exception("Invalid CSV format"))
      Try({
        Row(split.head, level, split.last.toInt)
      })
    }
  }
}
