package clients.v1

import db.Db
import doobie.implicits._
import javax.inject.{Inject, Singleton}
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future

@Singleton
class ClientDao @Inject()(db: Db) {

  def select(): Future[List[ClientDto]] = {
    sql"""
         select
           id,
           name,
           phone
         from clients
       """
      .query[ClientDto]
      .to[List]
      .transact(db.transactor)
      .runAsync
  }

  def get(id: Long): Future[Option[ClientDto]] = {
    sql"""
         select
           id,
           name,
           phone
         from clients
         where id = $id
       """
      .query[ClientDto]
      .option
      .transact(db.transactor)
      .runAsync
  }

  def insert(x: ClientData): Future[Int] = {
    sql"""
         insert into clients
           (
             name,
             phone
           )
         values
           (
             ${x.name},
             ${x.phone}
           )
       """
      .update
      .withUniqueGeneratedKeys[Int]("id")
      .transact(db.transactor)
      .runAsync
  }

  def update(id: Long, x: ClientData): Future[Unit] = {
    sql"""
          update clients set
            name = ${x.name},
            phone = ${x.phone}
          where id = $id
       """
      .update
      .run
      .transact(db.transactor)
      .runAsync
      .map(_ => ())
  }

  def delete(id: Long): Future[Unit] = {
    sql"""
          delete from clients
          where id = $id
       """
      .update
      .run
      .transact(db.transactor)
      .runAsync
      .map(_ => ())
  }
}
