package clients.v2

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import utils.NotFoundException

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ClientController @Inject()(cc: ControllerComponents,
                                 clientsDao: ClientDao) extends AbstractController(cc) {

  def getAll: Action[AnyContent] = Action.async { request: Request[AnyContent] =>
    clientsDao
      .select()
      .map(x => Ok(Json.toJson(x)))
  }

  def get(id: Long): Action[AnyContent] = Action.async { request: Request[AnyContent] =>
    clientsDao.get(id).map(_.getOrElse(throw NotFoundException()))
      .map(x => Ok(Json.toJson(x)))
  }

  def create(): Action[ClientDataDto] = Action.async(parse.json[ClientDataDto]) { request: Request[ClientDataDto] =>
    for {
      id <- clientsDao.insert(ClientData(request.body))
      x <- clientsDao.get(id).map(_.getOrElse(throw NotFoundException()))
    } yield Ok(Json.toJson(x))
  }

  def update(id: Long): Action[ClientDataDto] = Action.async(parse.json[ClientDataDto]) { request: Request[ClientDataDto] =>
    for {
      _ <- clientsDao.get(id).map(_.getOrElse(throw NotFoundException()))
      _ <- clientsDao.update(id, ClientData(request.body))
      x <- clientsDao.get(id).map(_.getOrElse(throw NotFoundException()))
    } yield Ok(Json.toJson(x))
  }

  def delete(id: Long): Action[AnyContent] = Action.async { request: Request[AnyContent] =>
    for {
      x <- clientsDao.get(id).map(_.getOrElse(throw NotFoundException()))
      _ <- clientsDao.delete(id)
    } yield Ok(Json.toJson(x))
  }

}
