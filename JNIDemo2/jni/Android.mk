LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#打包成函数库的名字
LOCAL_MODULE    := Funcs

#C代码文件
LOCAL_SRC_FILES := Funcs.c

include $(BUILD_SHARED_LIBRARY)