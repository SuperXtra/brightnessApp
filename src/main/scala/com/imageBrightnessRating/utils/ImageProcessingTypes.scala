package com.imageBrightnessRating.utils

import java.awt.image.BufferedImage
import java.io.File

import scala.collection.parallel.immutable.ParVector

object ImageProcessingTypes {

  type ImageNames = ParVector[String]
  type Images = ParVector[BufferedImage]
  type Files = ParVector[File]
  type Pixel = (Int, Int)
  type Brightnesses = ParVector[Double]

  class ProcessedImages(
                         val images: Images,
                         val fileNames: ImageNames)

}
