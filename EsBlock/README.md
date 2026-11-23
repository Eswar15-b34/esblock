# EsBlock

A black-themed Android app that blocks Instagram Reels and YouTube Shorts while leaving
messages, profiles and normal videos alone. It uses an accessibility service to notice the
Reels/Shorts screen and presses Back. No internet permission, no data collected.

## Get the APK (no Android Studio needed)

1. Create a new GitHub repository and upload everything in this folder
   (keep the `.github` folder).
2. Open the **Actions** tab, pick **Build APK**, and wait for the green check
   (or press **Run workflow**).
3. Open the finished run and download the **EsBlock-debug-apk** artifact (a zip).
   Unzip it to get `app-debug.apk`, then install it on your phone.

## Build with Android Studio instead

1. Open this folder in Android Studio and let it sync.
2. Choose **Build > Build APK(s)**. The file is `app/build/outputs/apk/debug/app-debug.apk`.

## First run on the phone

1. Install the APK (allow "install unknown apps" if asked).
2. Open EsBlock and tap **Open accessibility settings**.
3. Find **EsBlock**, switch it on, and accept the prompt.
4. If Android says the setting is restricted: App info > three-dot menu > **Allow restricted settings**, then try again.

## If blocking stops working

Instagram and YouTube rename their internal view IDs now and then. The IDs are listed at the
bottom of `app/src/main/kotlin/com/esblock/app/BlockerService.kt`; update them and rebuild.
