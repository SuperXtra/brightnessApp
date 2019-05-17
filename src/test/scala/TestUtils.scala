import java.io.File

import com.imageBrightnessRating.configuration.ImageBrightnessAppConfiguration

object TestUtils {

  def getTestPaths() = {
    val testImageBrightnessAppConfig = ImageBrightnessAppConfiguration.loadConfig("test.conf")
    val resourcesDirectory = new File("src/test/resources").getAbsolutePath()
    val fullInputPath = s"$resourcesDirectory${testImageBrightnessAppConfig.inputDirectory}"
    val fullOutputPath = s"$resourcesDirectory${testImageBrightnessAppConfig.outputDirectory}"
    (fullInputPath, fullOutputPath)
  }

}
