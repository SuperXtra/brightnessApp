import com.imageBrightnessRating.services.FileSystemService
import org.scalatest._

class FileSystemServiceTest extends FunSuite {

  test("ImageProcessingService.readImages - Missing extension") {
    val testPath : String = "src/test/resources/test_no_extension"
    val testAcceptedExtensions : List[String] = List("jpg")
    assertThrows[IllegalArgumentException](FileSystemService.readImages(testPath, testAcceptedExtensions))
  }


  test("ImageProcessingService.readImages - Non existing path") {
    val testPath : String = "src/test/resources/non_existing_folder"
    val testAcceptedExtensions : List[String] = List("jpg")
    assertThrows[IllegalArgumentException](FileSystemService.readImages(testPath, testAcceptedExtensions))
  }
}
