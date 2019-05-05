package com.imageBrightnessRating.services

import java.awt.image.BufferedImage

import scala.collection.parallel.immutable.ParVector
import scala.util.Random

object ResultsProcessingService {

  /**
    * Updates the name of an image including its brightness and classification (dark, light)
    *
    * */
  def updateImageNames(outputPath: String, results: ParVector[Double], originalName: ParVector[String], threshold: Int) : ParVector[String] = {

    (results zip originalName).map(tuple => {
      val (result, name) = tuple
      val lightOrDark = if (result <= threshold) "light" else "dark"
      val originalName = name.split('.')(0)
      val extension = name.split('.')(1)
      // in case of images with same name and brightness
      val smallGuid = (Random.alphanumeric take 4).mkString
      s"$outputPath/${originalName}_${lightOrDark}_${result.toInt.toString}_$smallGuid.$extension"
    })
  }

  /**
    * Useful for debugging. Measures execution time and clears output directory
    *
    * */
  def debug(outputPath: String, checkBrightnessFunction : () => Unit) ={

    FileSystemService.cleanDirectory(outputPath)

    val startTime = System.nanoTime

    checkBrightnessFunction()

    val duration = (System.nanoTime - startTime) / 1e9d

    println(s"Duration: $duration")
  }

  /**
    * Calculates average brightness of each image and updates an image name accordingly
    *
    * */
  def checkBrightness(processedImages: (ParVector[BufferedImage], ParVector[String]))(outputPath: String)(threshold: Int) : ParVector[String] = {

    val brightnesses : ParVector[Double] = processedImages._1.map(ImageProcessingService.calculateAverageBrightness)

    val updatedImageNames : ParVector[String] = updateImageNames(outputPath, brightnesses, processedImages._2, threshold)

    updatedImageNames
  }

}
