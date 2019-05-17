import com.imageBrightnessRating.configuration._
import com.imageBrightnessRating.services._
import com.imageBrightnessRating.utils.ImageProcessingTypes.{ImageNames, ProcessedImages}

object ImageBrightnessRatingApp {

  def main(args: Array[String]): Unit = {

    val imageBrightnessConfig = ImageBrightnessAppConfiguration.loadConfig("application.conf")

    val originalImages : ProcessedImages = FileSystemService.readImages(
      imageBrightnessConfig.inputDirectory,
      imageBrightnessConfig.acceptedExtensions)

    val preparedImages : ProcessedImages = ImageProcessingService.prepareImages(originalImages)

    val updatedImageNames : ImageNames = ResultsProcessingService.checkBrightness(preparedImages)(imageBrightnessConfig.outputDirectory)(imageBrightnessConfig.threshold)

    if (imageBrightnessConfig.debug) {
      ResultsProcessingService.debug(imageBrightnessConfig.outputDirectory, () => {
        FileSystemService.writeOutputImages(updatedImageNames,preparedImages.images)
      })
    } else {
      FileSystemService.writeOutputImages(updatedImageNames,originalImages.images)
    }

  }
}
