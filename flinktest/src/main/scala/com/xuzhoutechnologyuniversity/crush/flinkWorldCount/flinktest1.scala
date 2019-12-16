package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.{AggregateDataSet, DataSet, ExecutionEnvironment}

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      17
  * Author     Crush
  */
object flinktest1 {
  def main(args: Array[String]): Unit = {
    val tool: ParameterTool = ParameterTool.fromArgs(args)
    val inputpath: String = tool.get("input")
    val outputpath: String = tool.get("output")
    val environment: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val ds: DataSet[String] = environment.readTextFile(inputpath)
    import org.apache.flink.api.scala.createTypeInformation
    val result: AggregateDataSet[(String, Int)] = ds.flatMap(_.split(" ")).map((_,1)).groupBy(0).sum(1)
    result.writeAsCsv(outputpath).setParallelism(1)
    environment.execute()
  }
}
