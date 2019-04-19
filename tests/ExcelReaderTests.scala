import logic.{ExcelReader, NodeFactory, ParsedRow}
import org.scalatest.FlatSpec

class ExcelReaderTests extends FlatSpec {


  "getLines" should "read existing file" in {

    val reader = new ExcelReader("app/resources/test1.csv")

    reader.getLines.foreach(e => println(e))
  }
}
