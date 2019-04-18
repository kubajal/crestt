package logic

import play.api.libs.json.Json

case class Node(id: Int, name: String, nodes: List[Node]) {

    override def toString: String = {
      implicit val nodeFormat = Json.format[Node]
      Json.toJson(this).toString
    }
}