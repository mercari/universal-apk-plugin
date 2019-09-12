# Universal APK plugin

A gradle Plugin TODO

## Usage

TODO

### Gradle

For Gradle Kotlin DSL / an app module:

```kotlin
plugins {
    id("com.android.application")
    id("com.mercari.feature-flags")
}
```

For Gradle Groovy DSL / a library module:

```groovy
apply plugin: 'com.android.library'
apply plugin: 'com.mercari.feature-flags'
```

### Command Line

```kotlin
import com.example.app.fflag.Flags

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Flags.analytics) {
            TODO()
        }
    }
}
```

## Deploy

### Locally for testing

```
# Build plugin
./gradlew publishPluginMavenPublicationToMavenLocal
# Run samples
./gradlew -b samples/build.gradle.kts app:assembleDebug
./gradlew -b samples/build.gradle.kts app:generateDebugUniversalApk
```

### In Mercari artifactory

```
./gradlew artifactoryPublish -PartifactoryUser=$artifactory_user -PartifactoryKey=$artifactory_password
```


