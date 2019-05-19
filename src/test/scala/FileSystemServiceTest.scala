import com.imageBrightnessRating.services.FileSystemService
import org.scalatest._

class FileSystemServiceTest extends FunSuite {

  test("ImageProcessingService.readImages - Non existing path") {
    val testPath : String = ".src/test/resources/non_existing_folder"
    val testAcceptedExtensions : List[String] = List("jpg")
    val exception = intercept[IllegalArgumentException]{
      FileSystemService.readImages(testPath, testAcceptedExtensions)
    }
    assert(exception.getMessage.equalsIgnoreCase(s"Exception while reading images. $testPath does not exist."))
  }
}
