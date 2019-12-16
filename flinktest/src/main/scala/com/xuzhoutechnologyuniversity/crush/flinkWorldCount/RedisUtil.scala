package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      10
  * Author     Crush
  */
object RedisUtil {
  var jedisPool: JedisPool = null

  def getJedisClient(): Jedis = {
    if (jedisPool == null) {
      val config = PropertiesUtil.load("config.properties")
      val host = config.getProperty("redis.host")
      val port = config.getProperty("redis.port")

      val jedisPoolConfig: JedisPoolConfig = new JedisPoolConfig
      jedisPoolConfig.setMaxTotal(100) //设置最大的连接数
      jedisPoolConfig.setMaxIdle(20) //设置最大的闲置数
      jedisPoolConfig.setMinIdle(20) // 设置最小的闲置数
      jedisPoolConfig.setBlockWhenExhausted(true) // 忙碌时是否等待
      jedisPoolConfig.setMaxWaitMillis(500) // 设置等待的时长  毫秒
      jedisPoolConfig.setTestOnBorrow(true) // 每次获取连接测试

      jedisPool = new JedisPool(jedisPoolConfig, host, port.toInt)

    }


    jedisPool.getResource

  }
}