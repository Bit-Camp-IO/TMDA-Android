plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}
kotlin {
    jvmToolchain(8)
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {


    api(project(":sharedComponent"))
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi:1.15.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    implementation ("com.google.code.gson:gson:2.10.1")

    //Room
    implementation("androidx.room:room-common:2.5.1")
    ksp ("androidx.room:room-compiler:2.5.1")
    //Dagger
    implementation("javax.inject:javax.inject:1")

}
