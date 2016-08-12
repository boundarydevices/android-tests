package com.boundarydevices.i2capp;

/**
 * Wrapper Class only used to access JNI functions
 */
public class I2CDevice {
    static {
        System.loadLibrary("i2c-android-jni");
    }
    public native int open(int bus, int address);
    public native int readByte(int fd, int offset);
    public native int writeByte(int fd, int offset, byte value);
    public native int readBlock(int fd, int offset, byte[] buffer);
    public native int writeBlock(int fd, int offset, byte[] buffer, int count);
    public native void close(int fd);
}
