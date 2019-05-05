Image Brightness Rating App

To run this application use sbt command ~ run

----------------------------------------------------------------------

This application assesses the brightness level of an image and decides whether the image is bright or dark.
Application takes the images of defined extensions from input directory and saves the investigated files in output 
directory with the level of brightness and evaluation result included in the file name. 
Extensions and directories are defined in the configuration.
Evaluation of brightness is calculated by an algorithm using the RGB model.
The evaluation is done based on the defined threshold.

----------------------------------------------------------------------------
* Default configuration is defined in application.conf file, the app may be configured within the following values:
    - input_directory : path of the input data [String] - already existing one;
    - output_directory: path of the output data [String] - already existing one;
    - threshold: value of a threshold defining if image is classified as bright or dark [Integer] value between 0 and 100;
    - debug: defines if debug mode is on or off [Boolean] - True or False;
    - accepted_extensions: a list containing accepted image extensions [List of Strings] - example (jpg, png).
    

