buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0")
        classpath(kotlin("gradle-plugin", version = "1.3.21"))
    }
}

allprojects {
    // Mandatory for custom plugin transitive dependencies
    buildscript {
        repositories {
            google()
            jcenter()
        }
    }

    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
