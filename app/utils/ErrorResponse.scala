package utils

import play.api.libs.json.{JsValue, Json, OWrites}

case class ErrorResponse(errorId: String,
                         message: String,
                         info: Option[JsValue])

object ErrorResponse {
  implicit def writes: OWrites[ErrorResponse] = Json.writes[ErrorResponse]
}