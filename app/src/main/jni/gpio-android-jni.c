#include <jni.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <android/log.h>
#include <linux/i2c.h>
#include <linux/i2c-dev.h>
#include <jni.h>

/* Define Log macros */
#define  LOG_TAG    "gpio-android-jni"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL
Java_com_boundarydevices_gpioapp_GpioDevice_get(JNIEnv *env, jobject thiz, jint bank, jint pin)
{
    LOGD("Get gpio bank %d pin %d", bank, pin);
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_gpioapp_GpioDevice_set(JNIEnv *env, jobject thiz, jint bank, jint pin,
                                                jint value)
{
    LOGD("Set gpio bank %d pin %d to %d", bank, pin, value);
}