# OS-SDGP-G13
Open Science SDG Project - Group 13\
This contains the written source code of the Android Studio Project.\
IT DOES NOT INCLUDE THE ENTIRE ANDROID STUDIO PROJET!

## Objectives
Create an Android app that:\
  -Has a Legal section\
  -Has a Camera section\
    -Allows pictures to be saved\
    -Tags images through EXIF interface\
      -GPS location of device\
      -Orientation of device\
      -Additional descriptive

## Progress
Camera functionalities completed along with EXIF tags.
The EXIF tagging has been changed. To reach over 90% of devices, API 14 is being used, however the GPS EXIF tags require Rational types, which only appeared in API 21 or something. The Rotation tag also only took a single int because it isn't actually meant to store the actual device's rotation. So now there is only the EXIF TAG_IMAGE_DESCRIPTION being used, which stores a JSON of all data.

Remaining objectives for App:\
-complete description of legal sections
