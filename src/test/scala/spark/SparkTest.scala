package spark

import com.typesafe.config.ConfigFactory
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.collection.mutable
import scala.io.Source

/*
  Notes
  -----
  1. a driver is a client app with a main method. it can be run locally or on a cluster, across worker nodes by executors.
  2. a driver app contains one or more transformation * ---> 1 action chains. i.e., map -> filter -> reducebykey ->> collect
  3. a transformation yields an RDD or DStream. An RDD composes tunable partitions. A DStream composes RDDs.
  4. an action is a terminal operation that yields a numeric value or array of types, often simple tuples of numeric values.
  5. transformation * ---> 1 action chains are lazily evaluated upon invocation of a terminal action.
  6. invoking an action creates a job, composing one or more stages, each composing a set of executable tasks on RDDs.
  7. a job is structurally defined as a DAG of RDDs, which is translated into an execution plan
  8. jobs can execute locally or on a cluster, across worker nodes by executors.
 */
case class Person(age: Long, name: String)

class SparkTest extends FunSuite with BeforeAndAfterAll {
  val props = ConfigFactory.load("spark.properties")
  val conf = new SparkConf().setMaster(props.getString("spark.master")).setAppName(props.getString("spark.app.name"))
  val context = new SparkContext(conf)
  val streamingContext = new StreamingContext(context, Seconds(1))
  val sqlContext = new SQLContext(context)

  override protected def afterAll(): Unit = {
    super.afterAll
    streamingContext.awaitTerminationOrTimeout(2000)
    streamingContext.stop(stopSparkContext = true, stopGracefully = true)
  }

  test("transformations with action") {
    val rdd = context.makeRDD(Array(1, 2, 3)).cache
    assert(rdd.filter(_ % 2 == 0).first == 2)
    assert(rdd.filter(_ % 2 != 0).first == 1)
    assert(rdd.map(_ + 1).sum == 9)
    assert(rdd.map(_ + 1).collect sameElements Array(2, 3, 4))
  }

  test("actions") {
    val rdd = context.makeRDD(Array(1, 2, 3)).cache
    assert(rdd.count == 3)
    assert(rdd.first == 1)
    assert(rdd.min == 1)
    assert(rdd.max == 3)
    assert(rdd.mean == 2.0)
    assert(rdd.variance == 0.6666666666666666)
    assert(rdd.sampleVariance == 1.0)
    assert(rdd.stdev == 0.816496580927726)
    assert(rdd.sampleStdev == 1.0)
    assert(rdd.sum == 6)
    assert(rdd.fold(0)(_ + _) == 6)
    assert(rdd.reduce(_ + _) == 6)
    assert(rdd.take(1) sameElements Array(1))
    println(rdd.stats)
  }

  test("parallelize") {
    val data = 1 to 1000000
    val rdd = context.parallelize(data)
    val result = rdd.filter(_ % 2 == 0).collect
    assert(result.length == 500000)
  }

  test("partitioner") {
    val rdd = context.parallelize(List((1, 1), (2, 2), (3, 3))).partitionBy(new HashPartitioner(2)).persist
    val partitioner = rdd.partitioner.get // ShuffleRDDPartition @0 / @1
    assert(partitioner.numPartitions == 2)
  }

  test("aggregate") {
    val data = 1 to 10
    val rdd = context.parallelize(data)
    val (x, y) = rdd.aggregate((0, 0))((x, y) => (x._1 + y, x._2 + 1), (x, y) => (x._1 + y._1, x._2 + y._2))
    assert(x == 55 && y == 10)
  }

  test("sets") {
    val rdd1 = context.makeRDD(Array(1, 2, 3)).cache
    val rdd2 = context.makeRDD(Array(3, 4, 5)).cache
    assert(rdd1.union(rdd2).collect sameElements Array(1, 2, 3, 3, 4, 5))
    assert(rdd1.intersection(rdd2).collect sameElements Array(3))
    assert(rdd1.subtract(rdd2).collect.sorted sameElements Array(1, 2))
    assert(rdd2.subtract(rdd1).collect.sorted sameElements Array(4, 5))

    val rdd3 = context.makeRDD(Array(1, 1, 2, 2, 3, 3))
    assert(rdd3.distinct.collect.sorted sameElements Array(1, 2, 3))

    val rdd4 = context.makeRDD(Array(1, 2))
    val rdd5 = context.makeRDD(Array(3, 4))
    assert(rdd4.cartesian(rdd5).collect sameElements Array((1,3), (1, 4), (2, 3), (2, 4)))
  }

  test("reduce by key") {
    val rdd = context.makeRDD(Array((1, 1), (1, 2), (1, 3))).cache
    val (key, aggregate) = rdd.reduceByKey(_ + _).first
    assert(rdd.keys.collect sameElements Array(1, 1, 1))
    assert(rdd.values.collect sameElements Array(1, 2, 3))
    assert(key == 1 && aggregate == 6)
  }

  test("group by key") {
    val rdd = context.makeRDD(Array((1, 1), (1, 2), (1, 3))).cache
    val (key, list) = rdd.groupByKey.first
    assert(rdd.keys.collect sameElements Array(1, 1, 1))
    assert(rdd.values.collect sameElements Array(1, 2, 3))
    assert(key == 1 && list.sum == 6)
  }

  test("sort by key") {
    val rdd = context.makeRDD(Array((3, 1), (2, 2), (1, 3)))
    assert(rdd.reduceByKey(_ + _).sortByKey(ascending = true).collect sameElements Array((1,3), (2, 2), (3, 1)))
  }

  test("map values") {
    val rdd = context.makeRDD(Array((1, 1), (1, 2), (1, 3)))
    val (key, values) = rdd.mapValues(_ * 2).groupByKey.first
    assert(key == 1 && values.sum == 12)
  }

  test("text") {
    val rdd = context.textFile("license.mit").cache
    val totalLines = rdd.count
    assert(totalLines == 19)

    val selectedWordCount = rdd.filter(_.contains("Permission")).count
    assert(selectedWordCount == 1)

    val longestLine = rdd.map(l => l.length).reduce((a, b) => Math.max(a, b))
    assert(longestLine == 77)

    val wordCountRdd = rdd.flatMap(l => l.split("\\P{L}+")).map(_.toLowerCase).map(w => (w, 1)).reduceByKey(_ + _).cache
    val totalWords = wordCountRdd.count
    assert(totalWords == 96)

    val maxCount = wordCountRdd.values.max
    val (word, count) = wordCountRdd.filter(_._2 == maxCount).first
    assert(word == "the" && count == 14)
  }

  test("dataframes") {
    val df = sqlContext.read.json("src/test/resources/spark.data.frame.json.txt")
    df.printSchema
    df.show

    val names = df.select("name").orderBy("name").collect
    names.foreach(println)
    assert(names.length == 4)
    assert(names.head.mkString == "barney")

    val ages = df.select("age").orderBy("age").collect
    ages.foreach(println)
    assert(ages.length == 4)
    assert(ages.head.getLong(0) == 21)

    var row = df.filter(df("age") > 23).first
    assert(row.getLong(0) == 24)
    assert(row.getAs[String](1) == "fred")

    row = df.agg(Map("age" -> "max")).first
    assert(row.getLong(0) == 24)

    row = df.agg(Map("age" -> "avg")).first
    assert(row.getDouble(0) == 22.5)
  }

  /*
    A json schema is inferred and then sorted alphabetically by field name. This is incorrect behavior.
    Until a fix is found, case classes must be adjusted accordingly.
   */
  test("case class") {
    val personRdd = sqlContext.read.json("src/test/resources/spark.data.frame.json.txt")
      .map(p => Person(p(0).asInstanceOf[Long], p(1).asInstanceOf[String]))
    val personDf = sqlContext.createDataFrame[Person](personRdd)
    personDf.registerTempTable("persons")
    personDf.printSchema
    personDf.show
  }

  test("streaming") {
    val queue = mutable.Queue[RDD[String]]()
    val ds = streamingContext.queueStream(queue)
    streamingContext.checkpoint(System.getProperty("user.home") + "/.scala/spark/ds/checkpoint")
    queue += context.makeRDD(Source.fromFile("license.mit").getLines.toSeq)
    val wordCountDs = ds.flatMap(l => l.split("\\P{L}+")).map(_.toLowerCase).map(w => (w, 1)).reduceByKey(_ + _)
    wordCountDs.checkpoint(Milliseconds(1000))
    wordCountDs.saveAsTextFiles(System.getProperty("user.home") + "/.scala/spark/ds")
    wordCountDs.foreachRDD(rdd => {
      rdd.saveAsTextFile(System.getProperty("user.home") + "/.scala/spark/rdd")
    })
    streamingContext.start
  }
}