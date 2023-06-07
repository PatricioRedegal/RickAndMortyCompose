plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")

    //Dependency Injection
    implementation ("com.google.dagger:hilt-core:2.45")
    kapt ("com.google.dagger:hilt-compiler:2.45")

    //Test
    testImplementation (libs.junit)
    testImplementation ("io.mockk:mockk:1.13.5")

    //Modules
    implementation (project(":data"))
    implementation (project(":domain"))
}