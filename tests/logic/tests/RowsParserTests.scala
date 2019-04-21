package logic.tests

import logic.RowsParser
import org.scalatest.FlatSpec
import utils.POIRowCreator

import scala.util.Try

class RowsParserTests extends FlatSpec {

  val parser = new RowsParser()

  "parse function" should "fail on invalid CSV format" in {

    val data = "" :: "a;a;a" :: "aaaa" :: ";;;" :: "a;;;" :: ";;;a" :: Nil
    data
      .map(e => Try(parser.parse(0, e)))
      .foreach(e => assert(e.isFailure))
  }

  "parse function" should "succeed on zero level value" in {

    val data = "a;;;;1"
    val result = parser.parse(0, data)
    assert(result.level == 0)
    assert(result.id == 1)
    assert(result.value == "a")
  }

  "parse function" should "succeed on first level value" in {

    val data = ";a;;;1"
    val result = parser.parse(0, data)
    assert(result.level == 1)
    assert(result.id == 1)
    assert(result.value == "a")
  }

  "parse function" should "succeed on second level value" in {

    val data = ";;a;;1"
    val result = parser.parse(0, data)
    assert(result.level == 2)
    assert(result.id == 1)
    assert(result.value == "a")
  }

  "parse function" should "succeed on third level value" in {

    val data = ";;;a;1"
    val result = parser.parse(0, data)
    assert(result.level == 3)
    assert(result.id == 1)
    assert(result.value == "a")
  }

  "parse function" should "fail on apache.poi rows that are in wrong format" in {

    val creator = new POIRowCreator
    val data: List[List[String]] =
      List(
        Nil,
        List(""),
        List("a", "", "", "", "a", "s"),
        List("", "", ""),
        List("a", "", "", ""),
        List("", "", "", "a")
      )
    data.foreach(line => {
      val poirow = creator.createRow(line)
      val result = parser.parse(poirow)
      assert(result.isFailure)
    })
  }

  "parse function" should "create an apache.poi row given a correct 0-level row" in {

    val creator = new POIRowCreator
    val data = List("a", "", "", "", "1")
    val poirow = creator.createRow(data)
    val result = parser.parse(poirow).get
    assert(result.id == 1)
    assert(result.value == "a")
    assert(result.level == 0)
  }

  "parse function" should "create an apache.poi row given a correct 1-level row" in {

    val creator = new POIRowCreator
    val data = List("", "a", "", "", "1")
    val poirow = creator.createRow(data)
    val result = parser.parse(poirow).get
    assert(result.id == 1)
    assert(result.value == "a")
    assert(result.level == 1)
  }

  "parse function" should "create an apache.poi row given a correct 2-level row" in {

    val creator = new POIRowCreator
    val data = List("", "", "a", "", "1")
    val poirow = creator.createRow(data)
    val result = parser.parse(poirow).get
    assert(result.id == 1)
    assert(result.value == "a")
    assert(result.level == 2)
  }

  "parse function" should "create an apache.poi row given a correct 3-level row" in {

    val creator = new POIRowCreator
    val data = List("", "", "", "a", "1")
    val poirow = creator.createRow(data)
    val result = parser.parse(poirow).get
    assert(result.id == 1)
    assert(result.value == "a")
    assert(result.level == 3)
  }
}
