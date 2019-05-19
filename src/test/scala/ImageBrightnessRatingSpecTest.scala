import java.io.File

import com.imageBrightnessRating.model.CheckBrightnessJob
import com.imageBrightnessRating.services.{FileSystemService, ImageProcessingService, ResultsProcessingService}
import com.imageBrightnessRating.utils.ImageProcessingTypes.{ImageNames, ProcessedImages}
import org.scalatest.FlatSpec

class ImageBrightnessRatingSpecTest extends FlatSpec {

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

  val (testInputPath, testOutputPath) = TestUtils.getTestPaths()
  val imagesNames : ImageNames = new File(testInputPath).listFiles.map(_.getName()).toVector.par
  val darkImageNames : ImageNames = imagesNames.diff(lightImages)
  val testAcceptedExtensions : List[String] = List("jpg")
  val testThreshold : Int = 60

  val testImages : ProcessedImages = FileSystemService.readImages(testInputPath, testAcceptedExtensions)
  val testPreparedImages : ProcessedImages = ImageProcessingService.prepareImages(testImages)

  val testCheckBrightnessJob = new CheckBrightnessJob(
    processedImages =  testPreparedImages,
    acceptedExceptions = testAcceptedExtensions,
    outputPath = new String(),
    threshold = testThreshold
  )

  val updatedImageNames : ImageNames = ResultsProcessingService.checkBrightness(testCheckBrightnessJob)

  // Assertions:

  "Light images" should "be categorised as light" in {
    val resultsMap = (imagesNames zip updatedImageNames).toMap
    lightImages.forall(lightImage => resultsMap(lightImage).contains("light"))
  }

  "Dark images" should "be categorised as dark" in {
    val resultsMap = (imagesNames zip updatedImageNames).toMap
    darkImageNames.forall(darkImage => resultsMap(darkImage).contains("dark"))
  }

}
