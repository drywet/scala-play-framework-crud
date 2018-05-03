package db

import config.CfgHolder
import doobie.hikari._
import javax.inject.{Inject, Singleton}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.duration._

@Singleton
class Db @Inject()(cfg: CfgHolder) {

  val transactor: HikariTransactor[Task] = HikariTransactor.newHikariTransactor[Task](
    driverClassName = "org.postgresql.Driver",
    url = s"jdbc:postgresql:${cfg.cfg.database.database}?currentSchema=${cfg.cfg.database.schema}",
    user = cfg.cfg.database.user,
    pass = cfg.cfg.database.password
  ).runSyncUnsafe(10.seconds)

}