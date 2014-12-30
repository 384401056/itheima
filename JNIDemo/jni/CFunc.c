#include <stdio.h>
#include <jni.h>


/*
 * 参数类型JNIEnv* 就是jni.h中的 JNINativeInterface 结构体类型.
 * 通过此类型就可以调用结构体中的所有方法 。
*/

jstring com_blueice_demo_helloworldFromC(JNIEnv* env,jobject obj){

	/*
	 * 返回一个java类型的字符窜。
	 *
	 *	*env 相当于拿到 JNINativeInterface* JNIEnv 指向结构体的指针。
	 *
	 * 	**env 相当于拿到 JNINativeInterface结构体。
	 *
	 *
	 * 通过结构体调用NewStringUTF()方法 ，并传入参数。此方法就返回第二个参数的字符窜。
	 * 也可简写为(*env)->NewStringUTF(env, "http://121.42.44.149:801/ThmsApiV1.asmx");
	 */

	return (**env).NewStringUTF(env, "http://121.42.44.149:801/ThmsApiV1.asmx");

}

