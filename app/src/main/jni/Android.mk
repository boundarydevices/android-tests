LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_MODULE := gpio-android-jni
LOCAL_SRC_FILES := gpio-android-jni.c
LOCAL_LDLIBS := -llog
LOCAL_SHARED_LIBRARIES := libgpiod-static-jni

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE := libgpiod-static-jni
LOCAL_SRC_FILES := $(TARGET_ARCH_ABI)/libgpiod_static.a

include $(PREBUILT_STATIC_LIBRARY)