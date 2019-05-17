package com.imageBrightnessRating.configuration

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._

object ImageBrightnessAppConfiguration {

  class ImageBrightnessAppConfig(val inputDirectory: String,
                                 val outputDirectory: String,
                                 val threshold: Int,
                                 val debug: Boolean,
                                 val acceptedExtensions: List[String])

  def loadConfig(configPath: String) : ImageBrightnessAppConfig = {

    val configuration = ConfigFactory.load(configPath)

    new ImageBrightnessAppConfig(
      inputDirectory = configuration.getString("inputDirectory"),
      outputDirectory = configuration.getString("outputDirectory"),
      threshold = configuration.getString("threshold").toInt,
      acceptedExtensions = configuration.getStringList("acceptedExtensions").asScala.toList,
      debug = configuration.getBoolean("debug")
    )
  }
}
