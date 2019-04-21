package controllers

import javax.inject._
import logic.XLSX2JSON
import play.api.mvc._
import play.twirl.api.Html

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.main("Zadanie 2")(Html("")))
  }

}
