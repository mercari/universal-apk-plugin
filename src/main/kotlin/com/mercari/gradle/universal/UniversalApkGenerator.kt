package com.mercari.gradle.universal

import com.android.tools.build.bundletool.commands.BuildApksCommand
import com.android.tools.build.bundletool.model.Aapt2Command
import com.android.tools.build.bundletool.model.Password
import com.android.tools.build.bundletool.model.SigningConfiguration
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.security.KeyStore
import java.util.*
import java.util.function.Supplier
import java.util.zip.ZipFile

open class UniversalApkGenerator : DefaultTask() {

    @get:OutputFile
    lateinit var universalApkPath: File

    @get:Input
    lateinit var variantDirName: String
    @get:InputFile
    lateinit var inputBundle: File
    @get:InputFile
    lateinit var aapt2Path: File
    // Signing config
    @get:InputFile
    var storeFile: File? = null
    @get:Input
    var keyAlias: String? = null
    @get:Input
    var storePassword: String? = null
    @get:Input
    var keyPassword: String? = null

    @TaskAction
    fun execute() {
        val tempApksPath = project.buildDir.resolve("intermediates/apks/$variantDirName/universal.apks")

        val signingConfiguration = SigningConfiguration.extractFromKeystore(
            storeFile?.toPath(),
            keyAlias,
            storePassword?.optionalPassword(),
            keyPassword?.optionalPassword()
        )
        val apkCommandBuilder = BuildApksCommand.builder()
            .setBundlePath(inputBundle.toPath())
            .setOutputFile(tempApksPath.toPath())
            .setApkBuildMode(BuildApksCommand.ApkBuildMode.UNIVERSAL)
            .setOverwriteOutput(true)
            .setSigningConfiguration(signingConfiguration)
            .setAapt2Command(Aapt2Command.createFromExecutablePath(aapt2Path.toPath()))
            .setOutputPrintStream(System.out)
            .build()
        apkCommandBuilder.execute()

        // Create output file parents
        universalApkPath.parentFile.mkdirs()
        // Extract universal APK from apks file
        ZipFile(tempApksPath).use { zip ->
            val entry = zip.entries().asSequence().first { it.name == "universal.apk" }
            zip.getInputStream(entry).use { input ->
                universalApkPath.outputStream().use(input::copyTo)
            }
        }
    }
}

private fun String.optionalPassword() = Optional.of(Password.createForTest(this))
