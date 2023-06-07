plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

var moshiVersion = "1.14.0"
var retrofitVersion = "2.9.0"

dependencies {
    //Moshi
    implementation ("com.squareup.moshi:moshi:$moshiVersion")
    kapt ("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation ("com.squareup.retrofit2:converter-scalars:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // Dagger hilt
    implementation ("com.google.dagger:hilt-core:2.45")
    kapt ("com.google.dagger:hilt-compiler:2.45")

    // Rest
    implementation(libs.retrofit)

    // Modules
    implementation (project(":domain"))
}