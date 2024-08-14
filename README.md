# adb-remote-control

This is a simple tool to control an Android device remotely using ADB. Modified from [oberien/adb-remote-control](https://github.com/oberien/adb-remote-control).

## How to use
Compile the source code in the `src` folder. And run `javac Main.java && java Main`.

## Optimization
* Since the screencap from adb is quite slow, you can use the `adb shell wm size widthxheight` to change the resolution of the device to speed up the screencap.
* Also you can change the way to get the screen image, see `public BufferedImage screenshot() {...}` in `src/adb/Adbdevice.java`.