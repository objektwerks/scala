package spark

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.collection.mutable
import scala.io.Source

class SparkTest extends FunSuite with BeforeAndAfterAll {
  val conf = new SparkConf().setMaster("local[2]").setAppName("sparky")
  val context = new SparkContext(conf)
  val streamingContext = new StreamingContext(context, Seconds(1))

  override protected def afterAll(): Unit = {
    super.afterAll()
    streamingContext.stop(stopSparkContext = true, stopGracefully = true)
  }

  test("core") {
    val rdd = context.makeRDD(Array(1, 2, 3))
    assert(rdd.count == 3)
    assert(rdd.first == 1)
    assert(rdd.min == 1)
    assert(rdd.max == 3)
    assert(rdd.filter(_ % 2 == 0).first == 2)
    assert(rdd.filter(_ % 2 != 0).first == 1)
    assert(rdd.reduce(_ + _) == 6)
  }

  test("text") {
    val rdd = context.textFile("license.mit").cache()
    val lineCount = rdd.count()
    assert(lineCount == 19)
    assert(rdd.filter(_.contains("Permission")).count == 1)
    val longestLine = rdd.map(l => l.length).reduce((a, b) => Math.max(a, b))
    assert(longestLine == 77)
    val wordCountRdd = rdd.flatMap(l => l.split("\\P{L}+")).map(_.toLowerCase).map(w => (w, 1)).
      reduceByKey(_ + _).map(p => p.swap).sortByKey(ascending = false, 1).cache()
    val wordCount = wordCountRdd.count()
    assert(wordCount == 96)
    val (count, word) = wordCountRdd.max
    assert(count == 14)
    assert(word == "the")
  }

  test("parallelize") {
    val data = 1 to 1000000
    val rdd = context.parallelize(data)
    val result = rdd.filter(_ % 2 == 0).collect()
    assert(result.length == 500000)
  }

  test("streaming") {
    val queue = mutable.Queue[RDD[String]]()
    val ds = streamingContext.queueStream(queue)
    queue += context.makeRDD(Source.fromFile("license.mit").getLines().toSeq)
    val wordCountDs = ds.flatMap(l => l.split("\\P{L}+")).map(_.toLowerCase).map(w => (w, 1)).reduceByKey(_ + _)
    wordCountDs.saveAsTextFiles(System.getProperty("user.home") + "/.scala/spark/ds")
    wordCountDs.foreachRDD(rdd => {
      rdd.saveAsTextFile(System.getProperty("user.home") + "/.scala/spark/rdd")
    })
    streamingContext.start()
  }
}