import com.imageBrightnessRating.services.{FileSystemService, ImageProcessingService}
import com.imageBrightnessRating.utils.ImageProcessingTypes.ProcessedImages
import org.scalatest._

class ImageProcessingServiceTest extends FunSuite with BeforeAndAfter {


  val testImageName :String = "perfect_dark.jpg"
  val testPath : String = "src/test/resources/test_unit_image"
  val testImageExtension : List[String] = List("jpg")
  val processedImageSize = 32 // Image width / height after compression (processing)
  val expectedBrightness = 100

  val testImage : ProcessedImages = FileSystemService.readImages(testPath, testImageExtension)
  val testPreparedImage : ProcessedImages = ImageProcessingService.prepareImages(testImage)

  test("ImageProcessingService.readImages") {
    assert(testImage.images.length == 1)
    assert(testImage.fileNames.head.equals(testImageName))
  }

  test("ImageProcessingService.prepareImages") {
    assert(testPreparedImage.images.length == 1)
    assert(testPreparedImage.fileNames.head.equals(testImageName))
    assert(testPreparedImage.images.head.getWidth().equals(processedImageSize))
    assert(testPreparedImage.images.head.getHeight().equals(processedImageSize))
  }

  test("ImageProcessingService.calculateAverageBrightness") {
    assert(testPreparedImage.images.map(ImageProcessingService.calculateAverageBrightness).head == expectedBrightness)
  }

  test("ImageProcessingService.pixelBrightness") {
    println(ImageProcessingService.getPixelBrightness( (0,0) ,testImage.images.head) == 0.0)
  }





}
