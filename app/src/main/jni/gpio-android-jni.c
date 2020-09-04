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
#include <stdbool.h>
#include "gpiod.h"

/* Define Log macros */
#define  LOG_TAG    "gpio-android-jni"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL
Java_com_boundarydevices_gpioapp_GpioDevice_get(JNIEnv *env, jobject thiz, jint bank,
                                                jint pin, jboolean active_low)
{
    char device[16];
    snprintf(device, sizeof(device), "gpiochip%d", bank);
    LOGD("Get gpio bank %d pin %d", bank, pin);
    return gpiod_ctxless_get_value(device, pin, active_low, LOG_TAG);
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_gpioapp_GpioDevice_set(JNIEnv *env, jobject thiz, jint bank,
                                                jint pin, jboolean active_low, jint value)
{
    char device[16];
    snprintf(device, sizeof(device), "gpiochip%d", bank);
    LOGD("Set gpio bank %d pin %d to %d", bank, pin, value);
    return gpiod_ctxless_set_value(device, pin, value, active_low, LOG_TAG, NULL, NULL);
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_gpioapp_GpioDevice_waitPinEvent(JNIEnv *env, jobject thiz, jint bank,
                                                         jint pin, jint timeout_s)
{
    struct gpiod_chip *chip = gpiod_chip_open_by_number(bank);
    struct gpiod_line *line = gpiod_chip_get_line(chip, pin);
    struct timespec timeout = { .tv_sec = timeout_s, .tv_nsec = 0};

    int ret = gpiod_line_request_both_edges_events(line, LOG_TAG);
    if (ret < 0) {
        LOGE("Couldn't request gpio bank %d pin %d events", bank, pin);
        return ret;
    }

    ret = gpiod_line_event_wait(line, &timeout);

    gpiod_line_release(line);

    return ret;
}