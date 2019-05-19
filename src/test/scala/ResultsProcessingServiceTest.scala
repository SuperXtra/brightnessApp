import com.imageBrightnessRating.model.CheckBrightnessJob
import com.imageBrightnessRating.services.{FileSystemService, ImageProcessingService, ResultsProcessingService}
import com.imageBrightnessRating.utils.ImageProcessingTypes.ProcessedImages
import org.scalatest.{BeforeAndAfter, _}


class ResultsProcessingServiceTest extends FunSuite with BeforeAndAfter  {
  val testThreshold : Int = 60

  test("ResultsProcessingService.updateImageNames - DarkOne") {
    val testPath : String = "src/test/resources/test_dark_image"
    val testAcceptedExtensions : List[String] = List("jpg")
    val testImage : ProcessedImages = FileSystemService.readImages(testPath, testAcceptedExtensions)
    val testPreparedImage : ProcessedImages = ImageProcessingService.prepareImages(testImage)

    val testDarkOneJob = new CheckBrightnessJob(
      processedImages =  testPreparedImage,
      acceptedExceptions = testAcceptedExtensions,
      outputPath = new String(),
      threshold = testThreshold
    )
    assert(ResultsProcessingService.updateImageNames(testDarkOneJob,Vector(100.0).par).head.matches(raw"^\/dark_100_perfect_dark_(.){4}[.]jpg"))
  }


  test("ResultsProcessingService.updateImageNames - LightOne") {

    val testPath : String = "src/test/resources/test_light_image"
    val testAcceptedExtensions : List[String] = List("jpg")
    val testImage : ProcessedImages = FileSystemService.readImages(testPath, testAcceptedExtensions)
    val testPreparedImage : ProcessedImages = ImageProcessingService.prepareImages(testImage)

    val testLightOneJob = new CheckBrightnessJob(
      processedImages =  testPreparedImage,
      acceptedExceptions = testAcceptedExtensions,
      outputPath = new String(),
      threshold = testThreshold
    )
    assert(ResultsProcessingService.updateImageNames(testLightOneJob, Vector(0.0).par).head.matches(raw"^\/light_0_perfect_light_(.){4}[.]jpg"))
  }

  test("ResultsProcessingService.checkBrightness") {
    val testPath : String = "src/test/resources/test_dark_image"
    val testAcceptedExtensions : List[String] = List("jpg")
    val testImage : ProcessedImages = FileSystemService.readImages(testPath, testAcceptedExtensions)
    val testPreparedImage : ProcessedImages = ImageProcessingService.prepareImages(testImage)

    val testLightOneJob = new CheckBrightnessJob(
      processedImages =  testPreparedImage,
      acceptedExceptions = testAcceptedExtensions,
      outputPath = new String(),
      threshold = testThreshold
    )
    println(ResultsProcessingService.checkBrightness(testLightOneJob).head.matches(raw"^\/dark_100_perfect_dark_(.){4}[.]jpg"))
  }

}
