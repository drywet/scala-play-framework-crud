package clients.v2

import clients.Phone
import com.wix.accord.Validator
import com.wix.accord.dsl._
import play.api.libs.json._
import utils.ValidationUtil.validated

case class Client(id: Long,
                  name: String,
                  phone: Phone,
                  age: Option[Short])

case class ClientDto(id: Long,
                     name: String,
                     phone: String,
                     age: Option[Short])

object ClientDto {

  def apply(x: Client): ClientDto =
    ClientDto(
      id = x.id,
      name = x.name,
      phone = x.phone.phone,
      age = x.age
    )

  implicit val format: OFormat[ClientDto] = Json.format[ClientDto]

}

sealed abstract case class ClientData(name: String,
                                      phone: Phone,
                                      age: Option[Short])

object ClientData {

  def apply(name: String,
            phone: Phone,
            age: Option[Short]): ClientData = {
    validated(new ClientData(name.trim, phone, age) {})
  }

  def apply(x: ClientDataDto): ClientData =
    ClientData(
      name = x.name,
      phone = Phone(x.phone),
      age = x.age
    )

  implicit val validation: Validator[ClientData] =
    validator[ClientData] { x =>
      x.name is notBlank
      x.name has size <= 100
      x.phone is valid
      x.age.each is between(13.toShort, 150.toShort)
    }

}

case class ClientDataDto(name: String,
                         phone: String,
                         age: Option[Short])

object ClientDataDto {
  implicit val format: OFormat[ClientDataDto] = Json.format[ClientDataDto]
}