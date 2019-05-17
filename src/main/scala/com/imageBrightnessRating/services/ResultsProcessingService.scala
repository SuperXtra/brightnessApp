package com.imageBrightnessRating.services

import com.imageBrightnessRating.utils.ImageProcessingTypes.{Brightnesses, ImageNames, ProcessedImages}

import scala.util.Random

object ResultsProcessingService {

  /**
    * Updates the name of an image including its brightness and classification (dark, light)
    *
    * */
  def updateImageNames(outputPath: String, results: Brightnesses, originalNames: ImageNames, threshold: Int) : ImageNames = {

    (results zip originalNames).map({
      case (result, name) =>
        val lightOrDark = if (result <= threshold) "light" else "dark"
        val originalName = name.split('.').head
        val extension = name.split('.').last
        // in case of images with same name and brightness
        val smallGuid = (Random.alphanumeric take 4).mkString
        s"$outputPath/${originalName}_${lightOrDark}_${result.toInt.toString}_$smallGuid.$extension"
    })
  }

  /**
    * Useful for debugging. Measures execution time and clears output directory
    *
    * */
  def debug(outputPath: String, checkBrightnessFunction : () => Unit) : Unit ={

    FileSystemService.cleanDirectory(outputPath)

    val startTime = System.nanoTime

    checkBrightnessFunction()

    val duration = System.nanoTime - startTime

    println(s"Duration: $duration")
  }

  /**
    * Calculates average brightness of each image and updates an image name accordingly
    *
    * */
  def checkBrightness(processedImages: ProcessedImages)(outputPath: String)(threshold: Int) : ImageNames = {

    val brightnesses : Brightnesses = processedImages.images.map(ImageProcessingService.calculateAverageBrightness)

    val updatedImageNames : ImageNames = updateImageNames(
      outputPath,
      brightnesses,
      processedImages.fileNames,
      threshold)

    updatedImageNames
  }
}
