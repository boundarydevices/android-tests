#include <jni.h>
#include <stddef.h>

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_open(JNIEnv *env, jobject instance, jint bus,
                                               jint address) {

    // TODO

}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_readByte(JNIEnv *env, jobject instance, jint fd,
                                                   jint offset) {

    // TODO

}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_writeByte(JNIEnv *env, jobject instance, jint fd,
                                                    jint offset, jbyte value) {

    // TODO

}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_readBlock(JNIEnv *env, jobject instance, jint fd,
                                                    jint offset, jbyteArray buffer_, jint count) {
    jbyte *buffer = (*env)->GetByteArrayElements(env, buffer_, NULL);

    // TODO

    (*env)->ReleaseByteArrayElements(env, buffer_, buffer, 0);
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_writeBlock(JNIEnv *env, jobject instance, jint fd,
                                                     jint offset, jbyteArray buffer_, jint count) {
    jbyte *buffer = (*env)->GetByteArrayElements(env, buffer_, NULL);

    // TODO

    (*env)->ReleaseByteArrayElements(env, buffer_, buffer, 0);
}

JNIEXPORT void JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_close(JNIEnv *env, jobject instance, jint fd) {

    // TODO

}