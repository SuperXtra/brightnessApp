package com.imageBrightnessRating.model

import com.imageBrightnessRating.utils.ImageProcessingTypes.ProcessedImages

class CheckBrightnessJob(val processedImages: ProcessedImages,
                         val acceptedExceptions: List[String],
                         val outputPath: String,
                         val threshold: Int)
