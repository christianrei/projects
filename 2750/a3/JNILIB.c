#include <jni.h>
#include "JNILIB.h"
#include "ParameterManager.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

ParameterManager * p;
ParameterList * l;

/*
 * Class:     JNILIB
 * Method:    PM_create
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_JNILIB_pm_1create(JNIEnv *env, jobject obj, jint size) {
    p = PM_create(size);
}

/*
 * Class:     JNILIB
 * Method:    PM_destroy
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_JNILIB_pm_1destroy(JNIEnv *env, jobject obj) {
    int result;
    result = PM_destroy(p);
    return result;
}

/*
 * Class:     JNILIB
 * Method:    PM_hasValue
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_JNILIB_pm_1hasValue(JNIEnv *env, jobject obj, jstring pname) {
    const char *nativeString = (*env)->GetStringUTFChars(env, pname, 0);
    int check = PM_hasValue(p, (char*)nativeString);
    (*env)->ReleaseStringUTFChars(env, pname, nativeString);
    return check;
}

/*
 * Class:     JNILIB
 * Method:    PM_getValue
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_JNILIB_pm_1getValue(JNIEnv *env, jobject obj, jstring pname) {
    const char *nativeString = (*env)->GetStringUTFChars(env, pname, 0);
    jstring stringValue = NULL;
    char * sVal;
    int index = getIndex(p->parameters, (char*)nativeString);
    Parameter * temp = p->hash[index];
    
    while(temp != NULL) {
        if(strcmp(temp->title,(char*)nativeString)==0) {
            if(temp->type == INT_TYPE) {
                sprintf(sVal, "%d", PM_getValue(p, (char*)nativeString).int_val);
            }
            else if(temp->type == REAL_TYPE) {
                sprintf(sVal, "%lf", PM_getValue(p, (char*)nativeString).real_val);
            }
            else if(temp->type == BOOLEAN_TYPE) {
                sprintf(sVal, "%u", PM_getValue(p, (char*)nativeString).bool_val);
            }
            else if(temp->type == STRING_TYPE) {
                sVal = PM_getValue(p, (char*)nativeString).str_val;
            }
            else if(temp->type == LIST_TYPE) {
                l = PM_getValue(p, (char*)nativeString).list_val;
                sVal = PL_next(l);
            }
        }
        temp = temp->next;
    }
    stringValue = (*env)->NewStringUTF(env, sVal);
    (*env)->ReleaseStringUTFChars(env, pname, nativeString);
    return stringValue;
}

/*
 * Class:     JNILIB
 * Method:    getIndex
 * Signature: (ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_JNILIB_getIndex(JNIEnv *env, jobject obj, jint size, jstring title) {
    const char *nativeString = (*env)->GetStringUTFChars(env, title, 0);
    int index;
    index = getIndex(size, (char*)nativeString);
    (*env)->ReleaseStringUTFChars(env, title, nativeString);
    return index;
}

/*
 * Class:     JNILIB
 * Method:    PM_manage
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_JNILIB_pm_1manage(JNIEnv *env, jobject obj, jstring pname, jint type, jint required) {
    const char *nativeString;
    nativeString = (char*) (*env)->GetStringUTFChars(env, pname, NULL);
    int check;
    int theType = type;
    int isRequired = required;
    check = PM_manage(p, (char*)nativeString, theType, isRequired);
    (*env)->ReleaseStringUTFChars(env, pname, (char*)nativeString);
    return check;
}

/*
 * Class:     JNILIB
 * Method:    PM_parseFrom
 * Signature: (Ljava/lang/String;C)I
 */
JNIEXPORT jint JNICALL Java_JNILIB_pm_1parseFrom(JNIEnv *env, jobject obj, jstring fp, jchar comment) {
    const char *nativeString = (*env)->GetStringUTFChars(env, fp, 0);
    FILE * file;
    file = fopen("temp", "w");
    fprintf(file, "%s", (char*)nativeString);
    fclose(file);
    file = fopen("temp", "r");
    int check;
    check = PM_parseFrom(p, file, comment);
    fclose(file);
    (*env)->ReleaseStringUTFChars(env, fp, nativeString);
    return check;
}
