package com.imageBrightnessRating.services

import com.imageBrightnessRating.model.CheckBrightnessJob
import com.imageBrightnessRating.utils.ImageProcessingTypes.{Brightnesses, ImageNames}

import scala.util.Random

object ResultsProcessingService {

  /**
    * Updates the name of an image including its brightness and classification (dark, light)
    *
    * */
  def updateImageNames(checkBrightnessJob: CheckBrightnessJob, results: Brightnesses) : ImageNames = {

    val outputPath = checkBrightnessJob.outputPath
    val originalNames = checkBrightnessJob.processedImages.fileNames
    val acceptedExtensions = checkBrightnessJob.acceptedExceptions

    (results zip originalNames).map({
      case (result, name) => {

        val lightOrDark = if (result <= checkBrightnessJob.threshold) "light" else "dark"

        require(acceptedExtensions.exists(name.contains),s"File $name doesn't have extension")

        val nameExtensionSplit = name.split('.')
        val originalName = nameExtensionSplit.head
        val extension = nameExtensionSplit.last

        // in case of images with same name and brightness
        val smallGuid = (Random.alphanumeric take 4).mkString
        s"$outputPath/${lightOrDark}_${result.toInt.toString}_${originalName}_$smallGuid.$extension"
      }
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
  def checkBrightness(checkBrightnessJob: CheckBrightnessJob) : ImageNames = {

    val brightnesses : Brightnesses = checkBrightnessJob.processedImages.images.map(ImageProcessingService.calculateAverageBrightness)

    val updatedImageNames : ImageNames = updateImageNames(
      checkBrightnessJob,
      brightnesses)

    updatedImageNames
  }
}
