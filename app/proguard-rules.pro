-optimizationpasses 30
-mergeinterfacesaggressively
-dontpreverify
-optimizations !code/simplification/arithmetic
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''
-allowaccessmodification
-useuniqueclassmembernames
-keeppackagenames doNotKeepAThing
-ignorewarnings

-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt

-keepclasseswithmembernames public class * extends android.app.Activity{
    public protected *;
}
-keepclasseswithmembernames public class * extends android.content.BroadcastReceiver{
    public *;
}

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** d(...);
    public static *** e(...);
}


# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}
 
-keep public class * extends java.lang.Enum
 
 