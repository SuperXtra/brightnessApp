package com.imageBrightnessRating.services

import java.io.File

import com.imageBrightnessRating.utils.ImageProcessingTypes.{Files, ImageNames, Images, ProcessedImages}
import javax.imageio.ImageIO

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

    val filePaths : Files = new File(inputPath).listFiles.filter(
      x => EXTENSION_PATTERN.findAllIn(x.getName).length.equals(1)
    ).toVector.par

    new ProcessedImages(
      images = filePaths.map(ImageIO.read),
      fileNames = filePaths.map(x=>x.getName).toVector.par
    )
  }
}
