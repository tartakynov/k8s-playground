package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AppController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {
  /**
   * A REST endpoint that gets headers in the request.
   */
  def getHeaders = Action { implicit request =>
    val headersAsStrings = request.headers.headers.map { case (k, v) => s"$k: $v" }
    Ok(headersAsStrings.mkString("\n"))
  }
}
