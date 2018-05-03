package utils

import com.wix.accord.Violation
import play.api.http.Status
import play.api.libs.json.{Json, _}
import utils.ValidationException._

case class ValidationException(violations: Set[Violation])
  extends ApiException(
    Status.BAD_REQUEST,
    "Validation failed",
    Some(Json.toJson(violations))
  )

object ValidationException {
  implicit val violationWrites: Writes[Violation] = Writes[Violation](x => JsString(x.toString))
}