package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}
import org.apache.flink.table.calcite.FlinkRelBuilder

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      18
  * Author     Crush
  */
object MyRedisUtil {
  private val config: FlinkJedisPoolConfig = new FlinkJedisPoolConfig.Builder().setHost("hadoop").setPort(6379).build()

  def getRedisSink(): RedisSink[(String, String)] = {
    new RedisSink[(String, String)](config, new MyRedisMapper)
  }

  class MyRedisMapper extends RedisMapper[(String, String)] {
    override def getCommandDescription: RedisCommandDescription = {
      new RedisCommandDescription(RedisCommand.HSET, "channel_count")
    }

    override def getKeyFromData(data: (String, String)): String = data._1

    override def getValueFromData(data: (String, String)): String = data._2
  }

}
