buildscript {
    val kotlin_version = "1.6.10"

    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
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