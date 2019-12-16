package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      04
  * Author    Crush
  */
object WorldCountDemo {

  // 1 env //2 source //3 transform //4 sink

  def main(args: Array[String]): Unit = {
    val port = try {
      ParameterTool.fromArgs(args).getInt("port")
    }/*catch {
      case  e => {System.err.print("no port ,user need define port 9999")}
        9999
    }*/
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //读取数据源
    val text = env.socketTextStream("106.15.191.63",port,'\n')
    //需要去完成一个隐式转换

    import org.apache.flink.api.scala._
    val res = text.flatMap(line => line.split(" ")).map(word => WordCount(word, 1))
      .keyBy("word")
      .timeWindow(Time.seconds(2), Time.seconds(1))
      .sum("count")
    res.print()
    env.execute("scala world count")

  }
  case class WordCount(word:String,count:Int)
}
