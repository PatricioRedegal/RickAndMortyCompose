plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

buildscript{
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath(libs.android.tools.build.gradle)
        classpath(libs.kotlin.gradle.plugin)
    }
}
