package logic

import play.api.libs.json.Json

object Zadanie1 extends App {

  override def main(args: Array[String]): Unit = {
    val converter = new XLSX2JSON
    val json = Json.prettyPrint(
      Json.parse(converter.loadJson("test1.xlsx"))
    )
    println(json)
  }
}
