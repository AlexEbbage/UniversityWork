#!/usr/bin/env python

#===============================================================================
# PROGRAM DESCRIPTION
#===============================================================================
# PURPOSE
# The purpose of this program is to take images of the sky as command-line
# arguments, then to process the image in an efficient way to highlight the
# clouds in the image yellow. Then to output the original image alongside the
# processed image, for 2 seconds, before switching to the next image.

# USAGE
# Store the ForegroundMask.png and find-cloud.py in the same location.
# The command-line argument which is passed should be the image location and
# name. E.g. "cloudpix/*.jpg". If no arguments are passed then an error is
# thrown and the program terminates. Otherwise, an image is taken and processed,
# the output window will have the file location as a title.

# DECISIONS
# I tried several different filters to remove noise, and was using a Gaussian
# blur for a long time, but switched to a bilateral filter towards the end
# as it seemed to have a similar effect in regards to moving noise, but also
# preserved the edges better. This does however take longer to process than
# a Gaussian blur.
# I attempted using many different filters which CV2 provides, however found
# a function used in image processing programs called maxRGB which selects
# the highest colour channel for a given pixel and set the other channels
# which don't share the same value to 0. With a slight modification to the
# channel values, this distinguished clouds, sky and foreground objects very
# well. I then made a similarly functioning method called minRGB and another
# called differenceBetween which found the difference between 2 images, and
# used this to find the difference between the max and min RGB images. This
# produced an even better result.
# It was then a matter of finding the ranges in smaller sizes because if a
# range too large is selected, more value combinations are found and this can
#produce an unwanted result.

# VALUES
# To come up with the minRGB and maxRGB values, I could see that clouds came
# out as yellow more commonly, so chose to reduce the red and green values
# as this brought the clouds out even more. I came to the value -20 by
# gradually tweaking the values repeatedly until I settled up -20 producing
# the results which proved most useful.
# For the ranges, I used a tool in Adobe Photoshop called Color Range, which
# lets you select a pixel in the image, then highlights all the pixels with
# a similar colour value in the image. I screenshoted my difference images
# to Photoshop then repeatedly selected values, tweaking them over and over,
# until I found results that I thought were the best.
# However, this did involve a lot of compromise as by selecting some colour
# values which made one image produce better results, it would end up
# making over images produce worse results. I made a spreadsheet and looped
# through the images repeatedly after making tweaks, then took notes on each
# image. I then categorized images into good, average or bad results. I
# continued until I had a set of values which had most of the images in the
# good category.

# RESTRICTIONS
# The script picks up the edge of the gutter in the top-left in most of the
# images, as well as a bit of the sun glare in the top center for a few images,
# a few of the tree pixels in the bottom-right; especially in the brighter
# images.
# Some cloud pixels are missed, in most cases this was due to the way in which
# I selected the ranges for the masks. If I chose them I'd either get a lot of
# unwanted foreground objects, or a large amount of the bottom of the screen
# for evening skies where the sky is more yellow at the base, as well as the
# sun glare.
# There are 3 types images where this script does not work well, these images
# are ones where the image is other blue thunder clouds, a late evening sky
# with a yellow gradient at the bottom, or if there are long streaky clouds
# which have similar colours to the sky.
# Another restriction is that the program uses a mask image to remove the
# foreground objects which are common in most of the images, however without
# this image, the program will not run.

# AUTHOR
# Registration number 1504283
# I hereby certify that this program is entirely my own work.

import sys, cv2, numpy as np, pylab as plt, math

#===============================================================================
# ROUTINES
#===============================================================================
# MAX RGB
#-------------------------------------------------------------------------------
# Takes an image and a 3 value array as arguments. The 3 values are
# used to modify the values in the blue, green and red channels. The channels
# are split and a 2d array is produced containing the highest value of each
# pixel. The values of each channel are then checked if the value is less
# than the max value plus the modification, then it is set to 0. The 3 channels
# are then merged together again, producing an image where for each pixel, the
# channel with the highest value remains. The resulting image is mostly red,
# green and blue, but does have cyan, yellow, magenta and grey values as well.
#-------------------------------------------------------------------------------  

def maxRGB(image, maxVar):
    (b, g, r) = cv2.split(image)
    maxValue = np.maximum(np.maximum(r, g), b)
    b[b < (maxValue + maxVar[0])] = 0
    g[g < (maxValue + maxVar[1])] = 0
    r[r < (maxValue + maxVar[2])] = 0
    return cv2.merge([b, g, r])


#-------------------------------------------------------------------------------
# MIN RGB
#-------------------------------------------------------------------------------
# Takes an image and a 3-value array as arguments. The 3 values are
# used to modify the values in the blue, green and red channels. The channels
# are split and a 2d array is produced containing the lowest value of each
# pixel. The values of each channel are then checked if the value is greater
# than the min value plus the modification, then it is set to 0. The 3 channels
# are then merged together again, producing an image where for each pixel, the
# channel with the lowest value remains. The resulting image is mostly red,
# green and blue, but does have cyan, yellow, magenta and grey values as well.
#-------------------------------------------------------------------------------   

def minRGB(image, minVar):
    (b, g, r) = cv2.split(image)
    minValue = np.minimum(np.minimum(r, g), b)
    b[b > (minValue + minVar[0])] = 0  
    g[g > (minValue + minVar[1])] = 0    
    r[r > (minValue + minVar[2])] = 0       
    return cv2.merge([b, g, r])


#-------------------------------------------------------------------------------
# DIFFERENCE
#-------------------------------------------------------------------------------
# Takes an image and 2 3-value arrays as arguments. The first 3 value array
# controls the modifiying values used by the minRGB() method. The second 3 value
# array controls the modifying values used by the maxRBG() method. This method
# gets the minimum RGB image and negates its values from the maximum RGB image
# values. It then returns this new image.
#-------------------------------------------------------------------------------

def differenceBetween(image, minVar, maxVar):
    minImage = minRGB(image, minVar)
    maxImage = maxRGB(image, maxVar)
    difference = maxImage - minImage    
    return difference


#-------------------------------------------------------------------------------
# WHITE CLOUD FILTER
#-------------------------------------------------------------------------------
# Takes an image as an argument. Its purpose is to find all the bright white
# clouds. It finds values between a midtone yellow and white. A mask is produced
# where any values inside the range are white, and all others are black. This
# mask is returned for use later on.
#-------------------------------------------------------------------------------

def whiteCloudFilter(image):
    white_min = np.array([0, 100, 100], np.uint8)
    white_max = np.array([255, 255, 255], np.uint8)
    whiteClouds = cv2.inRange(image, white_min, white_max)
    whiteClouds = cv2.cvtColor(whiteClouds, cv2.COLOR_GRAY2RGB) 
    return whiteClouds

    
#-------------------------------------------------------------------------------
# MIDTONE CLOUD FILTER
#-------------------------------------------------------------------------------
# Takes an image as an argument. Its purpose is to find all the midtone clouds.
# It finds values between a dark red to a midtone yellow. A mask is produced
# where any values inside the range are white, and all others are black. This
# mask is returned for use later on.
#-------------------------------------------------------------------------------

def midtoneCloudFilter(image):
    grey_min = np.array([5,5,65], np.uint8)
    grey_max = np.array([100,170,170], np.uint8)
    midtoneClouds = cv2.inRange(image, grey_min, grey_max)
    midtoneClouds = cv2.cvtColor(midtoneClouds, cv2.COLOR_GRAY2RGB)   
    return midtoneClouds

    
#-------------------------------------------------------------------------------
# DARK CLOUD FILTER
#-------------------------------------------------------------------------------
# Takes an image as an argument. Its purpose is to find all the dark clouds and
# the clouds around the foreground objects which were missed. It finds several
# ranges in the image, each getting different areas as trying to get broad areas
# in one go produced poor results. It also allowed for much more specific values
# to be gained. All of the ranges produce a mask, and then they are combined at
# the end to produce the dark cloud mask. This is then returned.
#-------------------------------------------------------------------------------

def darkCloudFilter(difference):
    #Gets the dark yellow bits of the difference image.
    dark_min = np.array([0,15,10], np.uint8)
    dark_max = np.array([0,115,105], np.uint8)
    darkClouds = cv2.inRange(difference, dark_min, dark_max)
    darkClouds = cv2.cvtColor(darkClouds, cv2.COLOR_GRAY2RGB)

    #Gets the blue bits of the difference image.
    #ISSUES with 2 SIMILAR BLUES
    blue_min = np.array([150,8,8], np.uint8)
    blue_max = np.array([250,110,110], np.uint8)
    blueCloudsPositive = cv2.inRange(difference, blue_min, blue_max)
    blueCloudsPositive = cv2.cvtColor(blueCloudsPositive, cv2.COLOR_GRAY2RGB)

    #This isn't used because it has a greater effect on the dark cloud than
    #it does on the foreground objects.
    #Gets the blue values which need to be negated to reduce over-selection.
    #blue_min_neg = np.array([200,4,4], np.uint8)
    #blue_max_neg = np.array([255,50,50], np.uint8)
    #blueCloudsNegative = cv2.inRange(difference, blue_min_neg, blue_max_neg)
    #blueClouds = blueCloudsPositive - blueCloudsNegative
    #blueClouds = cv2.cvtColor(blueClouds, cv2.COLOR_GRAY2RGB)

    #Gets the green bits of the difference image.
    #ISSUES with GRADIENT SKIES and SUN FLARES
    #green_min = np.array([0,95,0], np.uint8)
    #green_max = np.array([95,230,0], np.uint8)
    #greenClouds = cv2.inRange(difference, green_min, green_max)
    #greenClouds = cv2.cvtColor(greenClouds, cv2.COLOR_GRAY2RGB)

    #Gets the dark grey bits of the difference image.
    grey_min = np.array([15,15,15], np.uint8)
    grey_max = np.array([75, 75, 75], np.uint8)
    greyClouds = cv2.inRange(difference, grey_min, grey_max)
    greyClouds = cv2.cvtColor(greyClouds, cv2.COLOR_GRAY2RGB)

    #Combines the masks into one final mask.
    maskCombination = darkClouds | blueCloudsPositive | greyClouds
    return maskCombination


#-------------------------------------------------------------------------------
# FOREGROUND OBJECT FILTER
#-------------------------------------------------------------------------------
# An image as taken as an argument. The purpose of this method is to find the
# forground objects such as the tree, the gutter and the spider. This mask will
# then be used to negate the effect of the other masks in areas where they are
# not wanted. I produced a mask using photoshop which fits the gutter perfectly,
# and the tree quite well; the gaps in the tree vary between images to I didn't
# want the data to be lost. This methods renders good results but doesn't help
# to remove random foreground objects, such as the spider, and wouldn't work for
# images taken in a different location i.e. where the tree and gutter is not.
#-------------------------------------------------------------------------------

def foregroundObjectFilter(image):
    foregroundMask = cv2.imread("ForegroundMask.png")
    return foregroundMask


#-------------------------------------------------------------------------------
# CLOUD MASK
#-------------------------------------------------------------------------------
# An image is taken as an argument. The image is blurred using bilateral
# filtering in order to reduce the noise in the image. This image will be
# filtered by being passed as an argument to the difference() method. This
# result is then used by the3 cloud mask methods to get the mask images from
# them. These masks are added together, then the foreground object mask is
# negated from the final mask. A copy of the image is taken, then where the
# final mask is white, the copy is set to yellow. The resulting image is
# supposed to be a copy of the original image, where all the clouds are
# highlighted yellow. There are a few areas of cloud which are missed which can
# be highlighted, however in doing so, it causes the sun flare and the lower
# part of the evening sky to be highlighted in a lot of images; so I decided to
# compromise and miss a bit of cloud. Some of the darker parts of cloud also
# aren't picked up, as it causes a lot of dark areas such as foreground objects
# to be picked up in other images, so I compromised again and opted for getting
# less detail over highlighting the wrong details.
#-------------------------------------------------------------------------------

def cloudMask(image):
    result = image.copy()
    copy = image.copy()
    blurredImage = cv2.bilateralFilter(copy, 3, 75, 75)
    
    #FILTERS
    difference = differenceBetween(image, (0,-20,-20), (0,-20,-20))

    #WHITE CLOUDS
    lightCloudMask = whiteCloudFilter(difference)

    #MIDTONE CLOUDS
    midtoneCloudMaskPrep = cv2.add(difference, lightCloudMask)
    midtoneCloudMask = midtoneCloudFilter(midtoneCloudMaskPrep)
 
    #DARK CLOUDS
    darkCloudMaskPrep = cv2.add(midtoneCloudMaskPrep, midtoneCloudMask)
    darkCloudMask = darkCloudFilter(darkCloudMaskPrep)

    #FOREGROUND OBJECTS
    foregroundMask = foregroundObjectFilter(image)

    #FULL MASK
    finalMask = (darkCloudMask | midtoneCloudMask | lightCloudMask) - foregroundMask
    result[np.where((finalMask == [255,255,255]).all(axis = 2))] = [0,255,255]    
    return result


#===============================================================================
# MAIN PROGRAM
#===============================================================================
# Handles command-line arguments, using them to select images for processing.
# Processes each image by combining a series of masks, which are supposed to
# find clouds in the images, then highlights those pixels yellow. An output of
# the original image next to the masked image is produced.
#-------------------------------------------------------------------------------
if len (sys.argv) < 2:
    print >>sys.stderr, "Usage:", sys.argv[0], "<image>..."
    sys.exit (1)

for fn in sys.argv[1:]:
    image = cv2.imread(fn)
    cloudMaskResult = cloudMask(image)
    resultStack = np.hstack((image, cloudMaskResult))
    cv2.imshow(fn, resultStack)
    cv2.moveWindow(fn, 0, 0)
    cv2.waitKey(2000)
    cv2.destroyWindow(fn)



#===============================================================================
# END OF PROGRAM
#===============================================================================
