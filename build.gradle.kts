import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.3.50"
    id("java-gradle-plugin")
    `maven-publish`
    id("com.jfrog.artifactory") version "4.9.8"
}

group = "com.mercari.gradle"
// TODO: use environment variable
version = "1.0"

gradlePlugin {
    plugins {
        create("UniversalApk") {
            // Follow https://docs.gradle.org/current/userguide/custom_plugins.html#sec:creating_a_plugin_id
            id = "$group.universal-apk"
            implementationClass = "com.mercari.gradle.universal.UniversalApkPlugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

repositories {
    google()
    jcenter()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    implementation("com.android.tools.build:gradle:3.5.0")
    implementation("com.android.tools.build:bundletool:0.9.0")

    testImplementation(gradleTestKit())
}
