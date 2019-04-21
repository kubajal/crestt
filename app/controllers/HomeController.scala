package controllers

import javax.inject._
import logic.XSLX2JSON
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    val converter = new XSLX2JSON
    val jsonString = converter.loadJson("test1.xlsx")
    Ok(views.html.index("Json: "))
  }

}
