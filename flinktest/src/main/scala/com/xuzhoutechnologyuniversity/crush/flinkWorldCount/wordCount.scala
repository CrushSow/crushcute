package com.xuzhoutechnologyuniversity.crush.flinkWorldCount


import org.apache.flink.api.java.operators.DataSource
import org.apache.flink.api.scala.{AggregateDataSet, DataSet, ExecutionEnvironment}

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      17
  * Author     Crush
  */
class wordCount {

}
object  wordCount{
  def main(args: Array[String]): Unit = {
    // env   // resource  // transform   //sink
    val environment: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    
    val input="file:///d:/hello.txt"
   val ds: DataSet[String] = environment.readTextFile(input)
    import org.apache.flink.api.scala.createTypeInformation
    val result: AggregateDataSet[(String, Int)] = ds.flatMap(_.split(" ")).map((_,1)).groupBy(0).sum(1)
   // result.write(new File)
  }
}