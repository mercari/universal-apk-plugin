package com.mercari.gradle.universal

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.file.Files
import java.nio.file.Paths

class UniversalApkPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val appExtension = project.extensions
            .findByType(AppExtension::class.java)
            ?: throw RuntimeException("Universal APK plugin should be applied after an android module")

        // Create tasks after evaluate, or we may use applicationVariants before its config
        project.afterEvaluate {
            appExtension.applicationVariants.all { setupUniversalApkGenerationTask(appExtension, this) }
        }
    }
}

fun Project.setupUniversalApkGenerationTask(appExtension: AppExtension, variant: ApplicationVariant) {
    val variantCapitalized = variant.name.capitalize()
    val projectName = name
    val signingConfig = variant.signingConfig
    val aapt2 = Paths.get(
        appExtension.sdkDirectory.toString(),
        "build-tools",
        appExtension.buildToolsVersion,
        "aapt2"
    )
    require(Files.exists(aapt2)) { "Couldn't find build-tools/${appExtension.buildToolsVersion} in ${appExtension.sdkDirectory}" }

    project.tasks.create(
        "generate${variantCapitalized}UniversalApk",
        UniversalApkGenerator::class.java
    ) {
        dependsOn("bundle$variantCapitalized")
        group = "Build"
        description = "Generates Universal APK from AAB for variant ${variant.name}"

        universalApkPath = buildDir.resolve("outputs/apk/${variant.dirName}/universal-${variant.name}.apk")
        variantDirName = variant.dirName
        inputBundle = buildDir.resolve("outputs/bundle/${variant.dirName}/$projectName.aab")
        storeFile = signingConfig.storeFile
        keyAlias = signingConfig.keyAlias
        storePassword = signingConfig.storePassword
        keyPassword = signingConfig.keyPassword
        aapt2Path = aapt2.toFile()
    }
}
