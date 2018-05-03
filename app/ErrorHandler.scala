import javax.inject.Singleton
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._
import utils.{ApiException, ErrorResponse}

import scala.concurrent.Future
import scala.util.Random

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    val errorId = generateErrorId
    Logger.error(s"Error $errorId: $message. Request: $request")
    Future.successful(
      Status(statusCode)(Json.toJson(
        ErrorResponse(
          errorId,
          message = message,
          info = None
        )
      ))
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    val errorId = generateErrorId
    Logger.error(s"Error $errorId: ${exception.getMessage}. Request: $request", exception)
    Future.successful(
      exception match {
        case e: ApiException => Status(e.status)(Json.toJson(
          ErrorResponse(
            errorId,
            message = e.message,
            info = e.info
          )
        ))
        case _ => InternalServerError(Json.toJson(
          ErrorResponse(
            errorId,
            message = "A server error occurred: " + exception.getMessage,
            info = None
          )
        ))
      }
    )
  }

  private def generateErrorId: String = Random.alphanumeric.take(5).mkString.toLowerCase
}