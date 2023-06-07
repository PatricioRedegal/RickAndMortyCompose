import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.dagger.hilt.android)
    kotlin ("android")
    kotlin("kapt")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.news.rick_and_morty_zara"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.news.rick_and_morty_zara"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        flavorDimensions.add("env")
        vectorDrawables {
            useSupportLibrary = true
        }

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    signingConfigs {
        create("config") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            @Suppress("UnstableApiUsage")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
        }
    }

    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            resValue ("string", "app_name", "Dev Rick And Morty")
        }

        create("pro") {
            dimension = "env"
            resValue ("string", "app_name", "Rick and Morty")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // AndroidX
    implementation (libs.core.ktx)
    implementation (libs.lifecycle.runtime.ktx)
    implementation (libs.activity.compose)

    // Compose
    implementation (platform(libs.androidx.compose.bom))
    implementation (libs.ui)
    implementation (libs.ui.graphics)
    implementation (libs.ui.tooling.preview)

    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    // SplashScreen
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // Material
    implementation (libs.androidx.compose.material)
    implementation (libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.iconsExtended)

    // Navigation
    implementation(libs.navigation.runtime.ktx)
    implementation(libs.navigation.compose)

    // Api Rest
    implementation(libs.retrofit)

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Load Images
    implementation(libs.landscapist.coil)
    implementation ("com.airbnb.android:lottie-compose:6.0.0")

    // Dependency injection
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.dagger.hilt.navigation)

    // Test
    testImplementation (libs.junit)

    // Modules
    implementation (project(":data"))
    implementation (project(":domain"))
    implementation (project(":usecases"))
}