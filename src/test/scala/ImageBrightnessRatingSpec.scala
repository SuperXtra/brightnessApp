import java.io.File

import com.imageBrightnessRating.services.{FileSystemService, ImageProcessingService, ResultsProcessingService}
import com.imageBrightnessRating.utils.ImageProcessingTypes.{ImageNames, ProcessedImages}
import org.scalatest.FlatSpec

class ImageBrightnessRatingSpec extends FlatSpec {

  val lightImages = List(
    "aidan_gillen.jpg",
    "conleth_hill.jpg",
    "emilia_clarke.jpg",
    "gwendoline_christie.jpg",
    "iain_glen.jpg",
    "john_bradley.jpg",
    "kit_harington.jpg",
    "lena_headey.jpg",
    "liamn_cunningham.jpg",
    "maisi_williams.jpg",
    "nathalie_emmanuel.jpg",
    "nikolaj_coster.jpg",
    "perfect_dark.jpg",
    "perfect_light.jpg",
    "peter_dinklage.jpg",
    "sopie_turner.jpg"
  )

  // Arrange
  val (testInputPath, testOutputPath) = TestUtils.getTestPaths()
  val imagesNames : Vector[String] = new File(testInputPath).listFiles.map(_.getName()).toVector
  val darkImages = imagesNames.diff(lightImages)
  val acceptedExtensions = List("jpg", "jpeg", "png")
  val images : ProcessedImages = FileSystemService.readImages(testInputPath, acceptedExtensions)
  val testThreshold : Int = 40
  // Act
  val preparedImages : ProcessedImages = ImageProcessingService.prepareImages(images)
  val updatedImageNames : ImageNames = ResultsProcessingService.checkBrightness(preparedImages)("")(testThreshold)

  // Assert
  "Light images" should "be categorised as light" in {
    val resultsMap = (imagesNames zip updatedImageNames).toMap
    lightImages.forall(lightImage => resultsMap(lightImage).contains("light"))
  }

  "Dark images" should "be categorised as dark" in {
    val resultsMap = (imagesNames zip updatedImageNames).toMap
    darkImages.forall(darkImage => resultsMap(darkImage).contains("dark"))
  }


}
