-keep class org.koin.** { *; }

-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keep class com.giraffe.repository.dto.** { *; }
-keepclassmembers class com.giraffe.repository.dto.** { *; }
-keepclassmembers class com.giraffe.repository.dto.** {
    public <init>(...);
}

-keep interface retrofit2.Call
-keep interface retrofit2.http.* { *; }
-keep interface com.giraffe.user.retrofit.** { *; }
-keep interface com.giraffe.media.retrofit.** { *; }
-keep class retrofit2.** { *; }

# Firebase
-keep class com.google.firebase.** { *; }
-keep class org.apache.http.** { *; }
-keepnames class com.google.firebase.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes SourceFile,LineNumberTable

-keepattributes KotlinMetadata
