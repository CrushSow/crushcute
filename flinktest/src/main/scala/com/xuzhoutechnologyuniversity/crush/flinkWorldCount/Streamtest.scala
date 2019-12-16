package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      17
  * Author     Crush
  */
object Streamtest {
  def main(args: Array[String]): Unit = {

    val tool: ParameterTool = ParameterTool.fromArgs(args)
    val host: String = tool.get("host")
    val port: String = tool.get("port")
    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    val ds: DataStream[String] = environment.socketTextStream(host,port.toInt)
    import org.apache.flink.api.scala._
    val result: DataStream[(String, Int)] = ds.flatMap(_.split(" ")).map((_,1)).keyBy(0).sum(1)
    result.print()
    environment.execute()

  }
}
