package logic
import scala.util.{Failure, Try}
import scala.util.parsing.combinator._

class RowsParser {

  def parse(level: Int, string: String): Try[Row] ={
    if(string.startsWith(";"))
      parse(level + 1, string.tail)
    else {
      val split = string.split(";")
      if(split.size != 2)
        Failure(new Exception("Invalid CSV format"))
      Try({
        Row(split.head, level, split.last.toInt)
      })
    }
  }
}
