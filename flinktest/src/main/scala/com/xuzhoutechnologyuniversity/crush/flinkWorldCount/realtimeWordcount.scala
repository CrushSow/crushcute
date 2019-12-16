package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      22
  * Author     Crush
  */
object realtimeWordcount {
  def main(args: Array[String]): Unit = {
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val source: DataStream[String] = environment.socketTextStream("192.168.207.110",9999)

    import org.apache.flink.api.scala._
    val dStream: DataStream[(String, Int)] = source.flatMap(_.split(" ")).filter(_.nonEmpty).map((_,1)).keyBy(0).sum(1)
    //打印
    dStream.print()

    environment.execute()


  }

}
