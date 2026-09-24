# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/ikemura/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn okio.**
-dontwarn javax.annotation.**

# Crashlytics: スタックトレースの行番号を残す
# https://firebase.google.com/docs/crashlytics/android/get-deobfuscated-reports
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# Firebase Components
# ComponentDiscovery が AndroidManifest の meta-data に記載された Registrar を
# 引数なしコンストラクタからリフレクションで生成するため keep する
# 削除されると "FirebaseCrashlytics component is not present." で起動時にクラッシュする
-keep class * implements com.google.firebase.components.ComponentRegistrar { <init>(); }

# Firebase Realtime Database
# https://firebase.google.com/docs/database/android/start#proguard
# getValue<T>() の GenericTypeIndicator がジェネリクス情報を参照できるようにする
-keepattributes Signature,InnerClasses,EnclosingMethod,*Annotation*
-keep class com.google.firebase.database.GenericTypeIndicator { *; }
-keep class * extends com.google.firebase.database.GenericTypeIndicator { *; }

# DataSnapshot.getValue() でデシリアライズする domain のモデル
# リフレクションでコンストラクタ・フィールド・getter/setter を名前で参照するため keep する
-keep class com.yaeyama_liner_checker.domain.top.** { *; }
-keep class com.yaeyama_liner_checker.domain.statusdetail.** { *; }
-keep class com.yaeyama_liner_checker.domain.weather.** { *; }
-keep class com.yaeyama_liner_checker.domain.time_table.** { *; }
-keep class com.yaeyama_liner_checker.domain.typhoon.** { *; }
