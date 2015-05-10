package spark

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

class SparkTest extends FunSuite {
  val conf = new SparkConf().setMaster("local").setAppName("sparky")
  val context = new SparkContext(conf)

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
}