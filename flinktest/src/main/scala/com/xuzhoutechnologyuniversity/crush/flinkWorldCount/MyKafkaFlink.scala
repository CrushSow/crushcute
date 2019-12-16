package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import java.util.Properties

import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}
import org.apache.flink.streaming.util.serialization.SimpleStringSchema





/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      17
  * Author     Crush
  */
object MyKafkaFlink {
  val properties: Properties = new Properties()
  properties.setProperty("kafka.broker.id", "hadoop:9092")
  properties.setProperty("groud.id", "gmall")


  def getConsumer(topic: String): FlinkKafkaConsumer[String] = {
    val consumer: FlinkKafkaConsumer[String] = new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties)
    consumer
  }

  def getProducer(topic:String):FlinkKafkaProducer[String]={
    new FlinkKafkaProducer[String]("gmall",topic,new SimpleStringSchema())

  }


}
