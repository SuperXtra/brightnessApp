import java.awt.image.BufferedImage

import com.imageBrightnessRating.services.{FileSystemService, ImageProcessingService, ResultsProcessingService}
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._

import scala.collection.parallel.immutable.ParVector

object ImageBrightnessRatingApp {
  def loadConfig(configPath: String) : (String,String,Int,Boolean, List[String]) = {
    val configuration = ConfigFactory.load(configPath)
    val inputPath = configuration.getString("input_directory")
    val outputPath = configuration.getString("output_directory")
    val threshold = configuration.getString("threshold").toInt
    val acceptedExtensions = configuration.getStringList("accepted_extensions").toList
    val debug = configuration.getBoolean("debug")
    (inputPath, outputPath, threshold, debug, acceptedExtensions)
  }

  def main(args: Array[String]): Unit = {

    val (inputPath, outputPath, threshold, debugMode, acceptedExtensions) = loadConfig("application.conf")

    val images : (ParVector[BufferedImage], ParVector[String]) = FileSystemService.readImages(inputPath, acceptedExtensions)

    val preparedImages : (ParVector[BufferedImage], ParVector[String]) = ImageProcessingService.prepareImages(images)

    val updatedImageNames : ParVector[String] = ResultsProcessingService.checkBrightness(preparedImages)(outputPath)(threshold)

    if (debugMode) {
      ResultsProcessingService.debug(outputPath, () => {
        FileSystemService.writeOutputImages(updatedImageNames,preparedImages._1)
      })
    } else {
      FileSystemService.writeOutputImages(updatedImageNames,images._1)
    }

  }
}
