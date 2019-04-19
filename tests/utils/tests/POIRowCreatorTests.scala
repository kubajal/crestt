package utils.tests

import org.scalatest.FlatSpec
import utils.POIRowCreator

class POIRowCreatorTests extends FlatSpec {

  "POIRowCreatorTests" should "create a poi.row given a correct list of values" in {
    val creator = new POIRowCreator
    val row  = creator.createRow(List("a", "", "b"))

    val cell0 = row.getCell(0).getStringCellValue
    val cell1 = row.getCell(1).getStringCellValue
    val cell2 = row.getCell(2).getStringCellValue
    assert(cell0 == "a")
    assert(cell1 == "")
    assert(cell2 == "b")
  }

  "POIRowCreatorTests" should "create an empty poi.row given an empty list of values" in {
    val creator = new POIRowCreator
    val row  = creator.createRow(List())

    assert(row.getCell(0) == null)
  }

  "POIRowCreatorTests" should "create an empty poi.row given a Nil" in {
    val creator = new POIRowCreator
    val row  = creator.createRow(Nil)

    assert(row.getCell(0) == null)
  }
}
