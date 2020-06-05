LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_MODULE := gpio-android-jni
LOCAL_SRC_FILES := gpio-android-jni.c
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)