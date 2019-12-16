package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      18
  * Author     Crush
  */
object FlinkFlink {


  /*
  *
  * Flink通过checkpoint来保存数据是否处理完成的状态

由JobManager协调各个TaskManager进行checkpoint存储，checkpoint保存在 StateBackend中，
默认StateBackend是内存级的，也可以改为文件级的进行持久化保存。
执行过程实际上是一个两段式提交，每个算子执行完成，会进行“预提交”，
直到执行完sink操作，会发起“确认提交”，如果执行失败，预提交会放弃掉。
如果宕机需要通过StateBackend进行恢复，只能恢复所有确认提交的操作。
  * */
  def main(args: Array[String]): Unit = {
   /* val consumer: FlinkKafkaConsumer[String] = MyKafkaFlink.getConsumer("first")

    val environment: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val datas: DataStream[String] = environment.addSource(consumer)
    datas.print()


    val kafkaProducer: FlinkKafkaProducer[String] = MyKafkaFlink.getProducer("second")

    // sink   kafka
    datas.map((_, 1)).map(Chcount => Chcount._1 + ":" + Chcount._2).addSink(kafkaProducer)

    // sink  redis
    datas.map((_, 1)).map(Chcount => (Chcount._1, Chcount._2 + "_")).addSink(MyRedisUtil.getRedisSink())

    environment.execute()*/


  }
}
