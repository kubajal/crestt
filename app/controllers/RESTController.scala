package controllers

import javax.inject.{Inject, Singleton}
import logic.XLSX2JSON
import play.mvc.Controller
import play.api.mvc._
import play.api.libs.json.Json

@Singleton
class RESTController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val converter: XLSX2JSON = new XLSX2JSON

  def getJson(path: String) = Action {
    try{
      val jsonString = converter.loadJson(path)
      val responseBody = Json.parse(jsonString)
      Ok(responseBody)
    }
    catch {
      case e: Exception => NotFound(views.html.notfound(path))
    }
  }
}
