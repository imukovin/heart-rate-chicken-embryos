buildscript {
    val kotlin_version = "1.6.10"

    repositories {
        mavenCentral()
        google()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("io.realm:realm-gradle-plugin:10.6.0")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://jitpack.io")
        flatDir {
            dirs("libs")
        }
    }
}

tasks {
    val clean = register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}