#include <jni.h>
#include <fcntl.h>
#include <stdio.h>
#include <android/log.h>
#include <linux/i2c.h>
#include <linux/i2c-dev.h>

/* Define Log macros */
#define  LOG_TAG    "i2c-android-jni"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static int devAddr;

int smbus_xfer(int fd, int read_write, int command, int size, union i2c_smbus_data *buffer)
{
    struct i2c_smbus_ioctl_data data;

    data.read_write = read_write;
    data.command = command;
    data.size = size;
    data.data = buffer;

    return ioctl(fd, I2C_SMBUS, &data);
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_open(JNIEnv *env, jobject instance, jint bus,
                                               jint address) {
    char dev[16];
    int fd;
    int ret;

    sprintf(dev, "/dev/i2c-%d", bus);
    LOGD("Opening %s", dev);
    fd = open(dev, O_RDWR);
    if (fd < 0) {
        LOGE("Couldn't open %s: %s", dev, strerror(errno));
        return fd;
    }

    LOGD("Setting address to %#02x", address);
    devAddr = address;
    ret = ioctl(fd, I2C_SLAVE_FORCE, address);
    if (ret != 0) {
        LOGE("Couldn't set slave address: %s", strerror(errno));
        return -1;
    }

    return fd;
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_readByte(JNIEnv *env, jobject instance, jint fd,
                                                   jint offset) {
    int ret;
    union i2c_smbus_data data;

    ret = smbus_xfer(fd, I2C_SMBUS_READ, offset, I2C_SMBUS_BYTE_DATA, &data);
    if (ret < 0) {
        LOGE("smbus_xfer read issue: %s", strerror(errno));
        return ret;
    }

    return (jint)data.byte;
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_writeByte(JNIEnv *env, jobject instance, jint fd,
                                                    jint offset, jbyte value) {
    union i2c_smbus_data data;

    data.byte = value;

    return smbus_xfer(fd, I2C_SMBUS_WRITE, offset, I2C_SMBUS_BYTE_DATA, &data);
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_readBlock(JNIEnv *env, jobject instance, jint fd,
                                                    jint offset, jbyteArray buffer_) {
    jbyte *buffer = (*env)->GetByteArrayElements(env, buffer_, NULL);

    union i2c_smbus_data data;
    int i;
    int ret;

    ret = smbus_xfer(fd, I2C_SMBUS_READ, offset, I2C_SMBUS_BLOCK_DATA, &data);
    if (ret < 0)
        return ret;

    for (i = 1; i <= data.block[0]; i++)
        buffer[i-1] = data.block[i];

    (*env)->ReleaseByteArrayElements(env, buffer_, buffer, 0);

    return data.block[0];
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_writeBlock(JNIEnv *env, jobject instance, jint fd,
                                                     jint offset, jbyteArray buffer_, jint count) {
    jbyte *buffer = (*env)->GetByteArrayElements(env, buffer_, NULL);

    union i2c_smbus_data data;
    int i;

    if (count > I2C_SMBUS_BLOCK_MAX) {
        LOGI("Reduce count %d to %d", count, I2C_SMBUS_BLOCK_MAX);
        count = I2C_SMBUS_BLOCK_MAX;
    }

    for (i = 1; i <= count; i++)
        data.block[i] = buffer[i-1];
    data.block[0] = count;

    (*env)->ReleaseByteArrayElements(env, buffer_, buffer, 0);

    return smbus_xfer(fd, I2C_SMBUS_WRITE, offset, I2C_SMBUS_BLOCK_DATA, &data);

}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_readBytesArray(JNIEnv *env, jobject instance, jint fd,
                                                         jint offset, jbyteArray buffer_, jint count) {
    jbyte *buffer = (*env)->GetByteArrayElements(env, buffer_, NULL);

    int ret;
    /* Create I2C messages */
    struct i2c_rdwr_ioctl_data data;
    struct i2c_msg msgs[2] = {
            {devAddr, 0, 1, &offset},
            {devAddr, I2C_M_RD, count, buffer},
    };

    data.msgs  = msgs;
    data.nmsgs = 2;
    ret = ioctl(fd, I2C_RDWR, &data);

    (*env)->ReleaseByteArrayElements(env, buffer_, buffer, 0);

    return ret;
}

JNIEXPORT jint JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_writeBytesArray(JNIEnv *env, jobject instance, jint fd,
                                                          jint offset, jbyteArray buffer_, jint count) {
    jbyte *buffer = (*env)->GetByteArrayElements(env, buffer_, NULL);

    int ret;
    int i;
    /* Allocate write buffer */
    uint8_t *buf = malloc(count + 1);
    if (buf == NULL)
        return -1;
    /* Create I2C messages */
    struct i2c_rdwr_ioctl_data data;
    struct i2c_msg msgs[1] = {
		{devAddr, 0, count + 1, buf},
	};

    /* Fill up write buffer */
    buf[0] = offset;
    for (i = 0; i < count; i++)
        buf[i+1] = buffer[i];

    data.msgs  = msgs;
    data.nmsgs = 1;
    ret = ioctl(fd, I2C_RDWR, &data);

    (*env)->ReleaseByteArrayElements(env, buffer_, buffer, 0);

    free(buf);

    return ret;

}

JNIEXPORT void JNICALL
Java_com_boundarydevices_i2capp_I2CDevice_close(JNIEnv *env, jobject instance, jint fd) {
    close(fd);
}