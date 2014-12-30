LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#打包成函数库的名字
LOCAL_MODULE    := CFunc

#C代码文件
LOCAL_SRC_FILES := CFunc.c

include $(BUILD_SHARED_LIBRARY)