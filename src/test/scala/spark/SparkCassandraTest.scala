package spark

import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector.embedded.EmbeddedCassandra
import com.datastax.spark.connector._
import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkContext, SparkConf}
import org.scalatest.{BeforeAndAfterAll, FunSuite}

/*
  Cassandra properties:

  (rpc_address,127.0.0.1)
  (jmx_port,7199)
  (cluster_name,Test Cluster0)
  (listen_address,127.0.0.1)
  (native_transport_port,9042)
  (ssl_storage_port,7100)
  (storage_port,7000)
  (seeds,127.0.0.1)

  Occasionally a NoHostAvailableException occurs. Running Cassandra from the console mysteriously resovles the error.
 */
class SparkCassandraTest extends FunSuite with BeforeAndAfterAll {
  val props = ConfigFactory.load("spark.properties")
  val cassandra = EmbeddedCassandra
  val conf = new SparkConf().setMaster(props.getString("spark.master"))
                            .setAppName(props.getString("spark.app.name"))
                            .set("spark.cassandra.connection.host", props.getString("spark.cassandra.connection.host"))
  val context = new SparkContext(conf)
  cassandra.getProps(0).foreach(println)


  override protected def beforeAll(): Unit = {
    super.beforeAll()
    CassandraConnector(conf).withSessionDo { session =>
      session.execute("CREATE KEYSPACE test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };")
      session.execute("CREATE TABLE test.kv(key text PRIMARY KEY, value int);")
      session.execute("INSERT INTO test.kv(key, value) VALUES ('k1', 1);")
      session.execute("INSERT INTO test.kv(key, value) VALUES ('k2', 2);")
      session.execute("INSERT INTO test.kv(key, value) VALUES ('k3', 3);")
    }
  }

  override protected def afterAll(): Unit = {
    super.afterAll
    CassandraConnector(conf).withSessionDo { session =>
      session.execute("DROP KEYSPACE test;")
    }
    context.stop
  }

  test("read") {
    val rdd = context.cassandraTable(keyspace = "test", table = "kv").cache
    assert(rdd.count == 3)
    assert(rdd.first.getInt("value") == 1)
    assert(rdd.map(_.getInt("value")).sum == 6.0)
  }

  test("write -> read") {
    val seq = context.parallelize(Seq(("k4", 4), ("k5", 5), ("k6", 6)))
    seq.saveToCassandra("test", "kv", SomeColumns("key", "value"))
    val rdd = context.cassandraTable(keyspace = "test", table = "kv").cache
    assert(rdd.count == 6)
    assert(rdd.map(_.getInt("value")).sum == 21.0)
  }
}