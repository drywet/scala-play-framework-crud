package clients

import com.wix.accord.Validator
import com.wix.accord.dsl._
import utils.ValidationUtil.validated

sealed abstract case class Phone(phone: String)

object Phone {

  def apply(phone: String): Phone = {
    validated(new Phone(
      phone
        .filter(_.isDigit)
        .map(_.getNumericValue)
        .mkString
    ) {})
  }

  implicit val validation: Validator[Phone] =
    validator[Phone] { x =>
      x.phone is notBlank
      x.phone has size >= 5
      x.phone has size <= 20
    }

}