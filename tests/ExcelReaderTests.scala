import logic.{ExcelReader, NodeFactory, ParsedRow, XLSX2JSON}
import org.scalatest.FlatSpec

class ExcelReaderTests extends FlatSpec {

  "parseToJson" should "return correct JSON strings given a correct .xlsx file" in {

    val processor = new XLSX2JSON
    val jsons = processor
      .getJsons("tests/resources/test1.xlsx")

    assert(jsons.contains("""{"id":11,"name":"D","nodes":[{"id":12,"name":"DA","nodes":[]}]}"""))
    assert(jsons.contains("""{"id":7,"name":"C","nodes":[{"id":8,"name":"CA","nodes":[{"id":9,"name":"CA1","nodes":[]},{"id":10,"name":"CA2","nodes":[]}]}]}"""))
    assert(jsons.contains("""{"id":6,"name":"B","nodes":[]}"""))
    assert(jsons.contains("""{"id":1,"name":"A","nodes":[{"id":2,"name":"AA","nodes":[{"id":3,"name":"AA1","nodes":[]},{"id":4,"name":"AA2","nodes":[]}]},{"id":5,"name":"AB","nodes":[]}]}"""))
    jsons.foreach(e => println(e))
  }

  "parseToJson" should "return empty JSON string list given a completly invalid .xlsx file" in {

    val processor = new XLSX2JSON
    val jsons = processor
      .getJsons("tests/resources/invalid1.xlsx")

    assert(jsons.isEmpty)
  }

  "parseToJson" should "return empty JSON string list given a .xlsx file with wrong indentation" in {

    val processor = new XLSX2JSON
    val jsons = processor
      .getJsons("tests/resources/invalid2.xlsx")

    println(jsons.head)
    assert(jsons.size == 1)
    assert(!jsons.head.contains(""""id":2"""))
  }
}
