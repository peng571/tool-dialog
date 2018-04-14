-optimizationpasses 5
-mergeinterfacesaggressively
-dontpreverify
-optimizations !code/simplification/arithmetic
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''
-allowaccessmodification
-useuniqueclassmembernames
-ignorewarnings

-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt

-dontwarn java.lang.invoke.**

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
   public static *;
}

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

