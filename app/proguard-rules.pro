# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/tony/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
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

###-----------基本配置-不能被混淆的------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
 
#support.v4/v7包不混淆
-keep class android.support.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v7.**
-keep interface android.support.v7.app.** { *; }
-dontwarn android.support.**    # 忽略警告

-dontshrink
-dontoptimize
-verbose
#
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }

-keepattributes Signature  
-keepattributes EnclosingMethod

-keep @interface com.meiyou.jet.annotation.**
#
#-keep class com.meiyou.jet.annotation.**{ *; }
-keep @com.meiyou.jet.annotation.JImplement class * { *; }
#
-keep @com.meiyou.jet.annotation.JProvider class * { *; }

#-keep class com.seeker.tony.myapplication.proxy.TestImpl {*;}

#
#-keep class com.seeker.tony.myapplication.proxy.Test{ *;}
