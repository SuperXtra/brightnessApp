import java.io.File

import ImageBrightnessRatingApp.loadConfig

object TestUtils {

  def getTestPaths() = {
    val (testInputPath, testOutputPath, _, _,_) = loadConfig("test.conf")
    val resourcesDirectory = new File("src/test/resources").getAbsolutePath()
    val fullInputPath = s"$resourcesDirectory$testInputPath"
    val fullOutputPath = s"$resourcesDirectory$testOutputPath"
    (fullInputPath, fullOutputPath)
  }

}
