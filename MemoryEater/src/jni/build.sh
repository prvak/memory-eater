#!/bin/sh
# Path to the android NDK.
NDK=$HOME/Software/android-ndk-r8e

# Other variables.
TOOLCHAIN=$NDK/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86_64/bin
GCC=$TOOLCHAIN/arm-linux-androideabi-gcc
PLATFORM=$NDK/platforms/android-14/arch-arm/
INC=$PLATFORM/usr/include
LIBS=$PLATFORM/usr/lib
TARGET=libnativebite.so
ZIPSTRUCTURE=lib/armeabi
JARFILE=armeabi.jar

# Compile library.
$GCC -c -fpic net_prvak_memoryeater_NativeBite.c -I $INC
$GCC -shared -nostdlib -L$LIBS -lc -W1 -o $TARGET net_prvak_memoryeater_NativeBite.o

# Pack the library as a jar file. Thanks to this hack will the library
# be part of the apk.
mkdir -p $ZIPSTRUCTURE
mv $TARGET $ZIPSTRUCTURE
zip -r $JARFILE lib/

# Move the fake jar file to the java libs directory.
mv $JARFILE ../../libs

# Cleanup.
rm -rf net_prvak_memoryeater_NativeBite.o lib/
