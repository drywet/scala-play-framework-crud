package config

import config.CfgHolder._
import javax.inject.Singleton

@Singleton
class CfgHolder() {

  val cfg: Cfg = pureconfig.loadConfigOrThrow[Cfg]

}

object CfgHolder {

  case class Cfg(database: DatabaseCfg)

  case class DatabaseCfg(host: String,
                         port: Int,
                         database: String,
                         user: String,
                         password: String,
                         schema: String)

}