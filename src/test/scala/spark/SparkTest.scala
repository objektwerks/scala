package spark

import org.apache.spark.{SparkContext, SparkConf}
import org.scalatest.FunSuite

class SparkTest extends FunSuite {
  test("spark") {
    val conf = new SparkConf().setMaster("local").setAppName("sparky")
    val context = new SparkContext(conf)
    val rdd = context.makeRDD(Array("a", "b", "c"))
    assert(rdd.count() == 3)
  }
}