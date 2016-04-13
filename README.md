# PhotoMosaicApp

This is a java application that transforms an image in a puzzle created with many other small images(tiles).
The application expects four arguments in the following order:

createImage(imagePath, TilesFolder, integer, destinationPath)

1- The path to the base image that is going to be converted;

2- The path to the tiles folder used to fill the base image;

3- A value used to choose which tile is going to fill each pixel of the base image (smaller means more specific tile, bigger means more tiles can occupy that pixel);

4- The path of the final image.


It's a basic program, still has a lot of work to do, so it is not very efficient.
