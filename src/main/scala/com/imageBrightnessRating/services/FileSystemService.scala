package com.imageBrightnessRating.services

import java.io.File

import com.imageBrightnessRating.utils.ImageProcessingTypes.{Files, ImageNames, Images, ProcessedImages}
import javax.imageio.ImageIO

import scala.util.Try

object FileSystemService {

  def cleanDirectory(path: String): Unit = {
    new File(path).listFiles.foreach(_.delete)
  }

  def writeOutputImages(updatedNames: ImageNames, files: Images): Unit = {
    (updatedNames zip files).foreach({
      case (name, file) =>
        val extension = name.split('.').last.toString
        ImageIO.write(file, extension, new File(s"$name"))
    })
  }

  def readImages(inputPath: String, acceptedExtensions: List[String]) : ProcessedImages = {

    val EXTENSION_PATTERN = s"[.](${acceptedExtensions.toArray.mkString("|")})".r

    try{

      val files = new File(inputPath).listFiles

      val filePaths : Files = files.filter(
        x => Try(EXTENSION_PATTERN.findAllIn(x.getName).length.equals(1)).getOrElse(false)
      ).toVector.par

      new ProcessedImages(
        images = filePaths.map(ImageIO.read),
        fileNames = filePaths.map(x=>x.getName).toVector.par
      )
    } catch {
      case e: NullPointerException => throw new IllegalArgumentException(s"Exception while reading images. $inputPath does not exist.")
    }
  }
}
