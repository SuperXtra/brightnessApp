import java.awt.image.BufferedImage
import java.io.File

import com.imageBrightnessRating.services.{FileSystemService, ImageProcessingService, ResultsProcessingService}
import org.scalatest.FlatSpec

import scala.collection.parallel.immutable.ParVector

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
  val images : (ParVector[BufferedImage], ParVector[String]) = FileSystemService.readImages(testInputPath, acceptedExtensions)

  // Act
  val preparedImages : (ParVector[BufferedImage], ParVector[String]) = ImageProcessingService.prepareImages(images)
  val updatedImageNames : ParVector[String] = ResultsProcessingService.checkBrightness(preparedImages)("")(40)

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
