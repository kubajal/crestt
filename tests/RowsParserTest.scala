package tests
import logic.{RowsParser, Row}
import org.scalatest.FlatSpec

class RowsParserTest extends FlatSpec {

  val parser = new RowsParser()

  "parse function" should "fail on invalid CSV format" in {

    val data = "" :: "a;a;a" :: "aaaa" :: ";;;" :: "a;;;" :: ";;;a" :: Nil
    data
      .map(parser.parse(0, _))
      .foreach(e => assert(e.isFailure))
  }

  "parse function" should "succeed on zero level value" in {

    val data = "a;;;;1"
    val result = parser.parse(0, data).get
    assert(result.level == 0)
    assert(result.id == 1)
    assert(result.value == "a")
  }

  "parse function" should "succeed on first level value" in {

    val data = ";a;;;1"
    val result = parser.parse(0, data).get
    assert(result.level == 1)
    assert(result.id == 1)
    assert(result.value == "a")
  }

  "parse function" should "succeed on second level value" in {

    val data = ";;a;;1"
    val result = parser.parse(0, data).get
    assert(result.level == 2)
    assert(result.id == 1)
    assert(result.value == "a")
  }

  "parse function" should "succeed on third level value" in {

    val data = ";;;a;1"
    val result = parser.parse(0, data).get
    assert(result.level == 3)
    assert(result.id == 1)
    assert(result.value == "a")
  }
}
