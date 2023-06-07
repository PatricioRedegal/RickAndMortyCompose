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
dependencies {
    //Moshi
    implementation ("com.squareup.moshi:moshi:$moshiVersion")
    kapt ("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
}