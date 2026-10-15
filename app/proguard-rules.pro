# Retrofit / Gson
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod
-keep class com.mikdadstream.app.data.remote.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn org.conscrypt.**

# ZXing (QR generate + scan)
-keep class com.google.zxing.** { *; }
-keep class com.journeyapps.barcodescanner.** { *; }
-dontwarn com.google.zxing.**
-dontwarn com.journeyapps.barcodescanner.**
