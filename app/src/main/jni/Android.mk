LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_MODULE := SerialPort
LOCAL_SRC_FILES := SerialPort.c
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
