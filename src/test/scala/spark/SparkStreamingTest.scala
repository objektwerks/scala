package spark

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.{SparkContext, SparkConf}
import org.scalatest.FunSuite

import scala.collection._

class SparkStreamingTest extends FunSuite {
  val conf = new SparkConf().setMaster("local[2]").setAppName("sparky")
  val context = new SparkContext(conf)

  test("text") {
    val streamingContext = new StreamingContext(context, Seconds(1))
    val queue = mutable.Queue[RDD[String]]()
    val ds = streamingContext.queueStream(queue)
    queue += context.makeRDD(Seq("Fred mowed the yard.", "Barney washed the car."))
    val wordCount = ds.flatMap(l => l.split("\\P{L}+")).map(_.toLowerCase).map(w => (w, 1)).reduceByKey(_ + _)
    wordCount.print()
    streamingContext.start()
    streamingContext.awaitTerminationOrTimeout(2000)
  }
}