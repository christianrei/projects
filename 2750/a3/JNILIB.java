/* JNI.java to intialize the native functions */
public class JNILIB {
    static {
        System.loadLibrary("JNIpm"); /* .so file */
   }
    public native void pm_create(int size);
    public native int pm_destroy();
    public native int pm_hasValue(String pname);
    public native String pm_getValue(String pname);
    public native int getIndex(int size, String title);
    public native int pm_manage(String pname, int type, int required);
    public native int pm_parseFrom(String fp, char comment);
}