package config

import com.typesafe.config.ConfigFactory
import org.scalatest.FunSuite

class ConfigTest extends FunSuite {
  test("load") {
    val config = ConfigFactory.load
    assert(config.getString("app.name") == "config")
    assert(config.getString("app.db.url") == "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:ddl.sql")
    assert(config.getString("app.db.driver") == "org.h2.Driver")
    assert(config.getString("app.http.host") == "localhost")
    assert(config.getInt("app.http.port") == 9999)
  }
}