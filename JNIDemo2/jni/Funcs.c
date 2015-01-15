#include <stdio.h>
#include <jni.h>
#include <string.h>


char* Jstring2CStr(JNIEnv*   env,   jstring   jstr)
{
	 char*   rtn   =   NULL;
	 jclass   clsstring   =   (*env)->FindClass(env,"java/lang/String");
	 jstring   strencode   =   (*env)->NewStringUTF(env,"GB2312");
	 jmethodID   mid   =   (*env)->GetMethodID(env,clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
	 jbyteArray   barr=   (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode); // String .getByte("GB2312");
	 jsize   alen   =   (*env)->GetArrayLength(env,barr);
	 jbyte*   ba   =   (*env)->GetByteArrayElements(env,barr,JNI_FALSE);
	 if(alen   >   0)
	 {
	  rtn   =   (char*)malloc(alen+1);         //"\0"
	  memcpy(rtn,ba,alen);
	  rtn[alen]=0;
	 }
	 (*env)->ReleaseByteArrayElements(env,barr,ba,0);  //
	 return rtn;
}


/**
 * 返回参数x,y相加后的值
 */
JNIEXPORT jint JNICALL Java_com_example_jnidemo2_DataProvider_add
  (JNIEnv * env, jobject obj, jint x, jint y)
{
	/**
	 * 由于C语言中的int和java中的int相同，所以这里可以直接返回。
	 */
	return x+y;


}

/**
 * 返回数据str加上特定字符串后的字符串值。 (字符串拼接)
 */
JNIEXPORT jstring JNICALL Java_com_example_jnidemo2_DataProvider_returnStr
  (JNIEnv * env, jobject obj, jstring str)
{

	char * source = "Hello";

	char * destination = Jstring2CStr(env,str);//将Java中String类型的参数转为char *。

	strcat(destination,source);

	return (*env)->NewStringUTF(env, destination);//将C语言中的char *转为String。
}

/**
 * 对传入的int数组进行+5操作后返回一个数组。
 */
JNIEXPORT jintArray JNICALL Java_com_example_jnidemo2_DataProvider_intMethod
  (JNIEnv * env, jobject obj, jintArray jarray)
{
	//1.需要获取数组长度。jsize (*GetArrayLength)(JNIEnv*, jarray);
	int len = (*env)->GetArrayLength(env,jarray);


	//2.需要获取传入数组的首地址。jint* (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
	int* array = (*env)->GetIntArrayElements(env,jarray,0);


	//3.遍历数组，对每个值加上5。
	int i=0;
	for(;i<len;i++){
		*(array+i)+=5;
	}

	//返回jintArray。
	return jarray;
}



















