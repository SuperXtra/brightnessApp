package com.imageBrightnessRating.services

import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

import scala.collection.parallel.immutable.ParVector

object FileSystemService {

  def cleanDirectory(path: String): Unit = {
    new File(path).listFiles.foreach(_.delete)
  }

  def writeOutputImages(updatedNames: ParVector[String], files: ParVector[BufferedImage]): Unit = {
    (updatedNames zip files).foreach(tuple => {
      val (name, file) = tuple
      val extension = name.split('.')(2).toString
      ImageIO.write(file, extension, new File(s"$name"))
    })
  }

  def readImages(inputPath: String, acceptedExtensions: List[String]) : (ParVector[BufferedImage], ParVector[String]) = {
    val EXTENSION_PATTERN = s"[.](${acceptedExtensions.toArray.mkString("|")})".r
//    val filePaths : ParVector[File] = new File(inputPath).listFiles.filter(x=>acceptableDataFormats contains x.getName.split('.')(1)).toVector.par
    val filePaths : ParVector[File] = new File(inputPath).listFiles.filter(x=>EXTENSION_PATTERN.findAllIn(x.getName).length.equals(1)).toVector.par

    val images : ParVector[BufferedImage] = filePaths.map(ImageIO.read)
    val originalNames: ParVector[String] = filePaths.map(x=>x.getName).toVector.par
    (images, originalNames)
  }
}
