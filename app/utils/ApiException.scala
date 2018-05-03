package utils

import play.api.libs.json._

class ApiException(val status: Int,
                   val message: String,
                   val info: Option[JsValue] = None,
                   val cause: Option[Throwable] = None)
  extends RuntimeException(message, cause.orNull)

object ApiException {
  implicit val writes: OWrites[ApiException] = OWrites[ApiException](x => JsObject(Map(
    "message" -> JsString(x.message),
    "info" -> Json.toJson(x.info)
  )))
}