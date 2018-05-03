package utils

import com.wix.accord.{Failure, Result, Validator, validate}

object ValidationUtil {
  def checkSuccess(result: Result): Unit = result match {
    case Failure(violations) => throw ValidationException(violations)
    case _ =>
  }

  def validated[T](x: T)(implicit validator: Validator[T]): T = {
    checkSuccess(validate(x))
    x
  }
}
