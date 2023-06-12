import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")

}
kotlin {
    jvmToolchain(8)
}


val localProps = Properties()
val localPropsFile = localProps.load(project.rootProject.file("local.properties").inputStream())

val hilt = "2.46.1"
val retrofit = "2.9.0"


android {
    namespace = "com.bitIO.tvshowcomponent"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
        buildConfigField("String", "API_KEY", localProps.getProperty("apiKey"))
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")


    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("androidx.room:room-common:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    implementation("javax.inject:javax.inject:1")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
