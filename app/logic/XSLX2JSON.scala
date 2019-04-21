package logic

import javax.inject.Singleton

import scala.util.Success

class XSLX2JSON {

  def loadJson(path: String): String = {

    """{"jsons":[""" +
      getJsons("app/resources/" + path)
        .reduce((e, f) => e + ", " + f) + """]}"""
  }


  def getJsons(path: String): List[String] = {

    val reader = new ExcelReader(path)

    val rowsParser = new RowsParser
    val rows = reader
      .getPoiRows
      .toList
      .map(e => rowsParser.parse(e))

    val successful = for {
      Success(e) <- rows
    }
      yield e

    val nodeFactory = new NodeFactory
    nodeFactory
      .parse(successful)
      .map(e => e.toString)

  }
}
