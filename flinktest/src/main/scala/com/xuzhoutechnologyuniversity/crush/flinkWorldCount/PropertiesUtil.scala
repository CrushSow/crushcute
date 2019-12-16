package com.xuzhoutechnologyuniversity.crush.flinkWorldCount

import java.io.InputStreamReader
import java.util.Properties

import scala.tools.nsc.interpreter.InputStream

/**
  *
  * PACKAGE_NAMW   com.xuzhoutechnologyuniversity.crush.flinkWorldCount
  * DATE      10
  * Author     Crush
  */
object PropertiesUtil {
  def load(propertieName: String): Properties = {
    var properties: Properties = new Properties()
    val stream = Thread.currentThread().getContextClassLoader.getResourceAsStream(propertieName)
    properties.load(new InputStreamReader(stream, "UTF-8"))
    properties
  }
}
