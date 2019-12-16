package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import java.util

import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSinkFunction, RequestIndexer}
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink
import org.apache.http.HttpHost
/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      18
  * Author     Crush
  */
object MyElasticSearch {
  val httpHosts=new util.ArrayList[HttpHost]

  httpHosts.add(new HttpHost("hadoop",9092,"http"))

  /*def gerElasticSearch(indexName:String):ElasticsearchSink[String]= {
    val esFunc: ElasticsearchSinkFunction[String] = new ElasticsearchSinkFunction[String] {
      override def process(t: String, runtimeContext: RuntimeContext, requestIndexer: RequestIndexer): Unit = {


      }

    }
  }*/

}
