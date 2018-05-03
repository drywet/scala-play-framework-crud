package utils

import play.api.http.Status

case class NotFoundException() extends ApiException(Status.NOT_FOUND, "Not found")

