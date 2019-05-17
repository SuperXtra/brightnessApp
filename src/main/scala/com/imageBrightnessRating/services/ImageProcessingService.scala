package com.imageBrightnessRating.services

import java.awt.Image
import java.awt.image.{BufferedImage, ConvolveOp, Kernel}

import com.imageBrightnessRating.utils.ImageProcessingTypes.{Brightnesses, Pixel, ProcessedImages}

import scala.collection.parallel.immutable.ParVector

object ImageProcessingService {

  /**
    * Calculate average brightness of a image
    *
    * */
  def calculateAverageBrightness(image: BufferedImage): Double = {

    val height: Int = image.getHeight
    val width: Int = image.getWidth

    val pixels : ParVector[Pixel] = ((0 until width) zip (0 until height)).toVector.par

    val brightnessScores : Brightnesses = for {
      pixel <- pixels
    } yield 100 - getPixelBrightness(pixel, image)

    val averageBrightness : Double = brightnessScores.foldLeft(0.0)(_ + _) / brightnessScores.length

    Math.abs(averageBrightness.toInt)
  }

  /**
    * Returns the brightness of specified by x and y pixel on image
    *
    * */
  def getPixelBrightness(pixel: Pixel, image: BufferedImage): Double = {
    val (x, y) = pixel
    val color = image.getRGB(x, y)
    val red = color & 0x00ff0000 >> 16
    val green = (color & 0x0000ff00) >> 8
    val blue = color & 0x000000ff
    Math.sqrt(0.299 * (red * red) + 0.587 * (green * green) + 0.114 * (blue * blue)) / 255 * 100
  }

  /**
    * Apply blur filter of 2x2 kernel of 1/2
    *
    * */
  def blurImage(image: BufferedImage): BufferedImage = {

    val kernel = new Kernel(2, 2, Array.fill(2 * 2)(1f / 2f))
    val op = new ConvolveOp(kernel)

    val bufferedImage = op.filter(image, null)

    bufferedImage
  }

  /**
    * Reduces the side of an image
    *
    * */
  def sizeReduction(image: BufferedImage, size: Int): BufferedImage = {

    val rescaled : Image = image.getScaledInstance(size, size, Image.SCALE_DEFAULT)

    val rescaledBuffered : BufferedImage = new BufferedImage(size, size, Image.SCALE_DEFAULT)

    rescaledBuffered.getGraphics.drawImage(rescaled, 0, 0, null)

    rescaledBuffered
  }

  /**
    * Processes images
    *
    * Reduces the size to 32x32 and blurs collection of images
    *
    * */
  def prepareImages(inputImages : ProcessedImages) : ProcessedImages = {

    val processed = inputImages.images
      .map(sizeReduction(_, 32))
      .map(blurImage)

    new ProcessedImages(
      images = processed,
      fileNames = inputImages.fileNames)
  }
}
