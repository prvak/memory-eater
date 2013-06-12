#include "net_prvak_memoryeater_NativeBite.h"
#include <stdlib.h>

JNIEXPORT jint JNICALL Java_net_prvak_memoryeater_NativeBite_allocate
  (JNIEnv *jni, jobject object, jint size) {
    char *bytes = (char*) malloc(size);
    if (bytes == NULL) {
        return (jint) 0;
    } else {
        // Memory must be touched before it is recognized as used.
        memset(bytes, '\0', size);
        return (jint) bytes;
    }
}
