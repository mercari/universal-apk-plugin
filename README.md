# Universal APK plugin

🚨 Deprecated: use [AGP provided tasks](https://pgreze.netlify.com/post/2019-10-02-universal-apk-commands/).

## Usage

Build and install a debug universal APK:

```
./gradlew -b samples/build.gradle.kts app:packageDebugUniversalApk
adb install samples/app/build/outputs/universal_apk/debug/app-debug-universal.apk
```
