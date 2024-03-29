plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
val hilt = "2.46.1"

android {
    namespace = "com.example.sharedui"
    compileSdk = 33

    defaultConfig {
        minSdk = 26


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlin {
        jvmToolchain(8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.05.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    api("androidx.core:core-ktx:1.10.1")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    api("androidx.activity:activity-compose:1.7.2")
    api("androidx.navigation:navigation-compose:2.6.0")
    api("androidx.compose.material3:material3")
    api("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha10")


    //hilt
    api("androidx.hilt:hilt-navigation-compose:1.0.0")
    api("com.google.dagger:hilt-android:$hilt")
    kapt("com.google.dagger:hilt-compiler:$hilt")


    //Paging
    api("androidx.paging:paging-runtime-ktx:3.1.1")
    api("androidx.paging:paging-compose:3.2.0-rc01")

    //Coil
    api("io.coil-kt:coil-compose:2.4.0")
    //Lottie
    api("com.airbnb.android:lottie-compose:6.0.1")
    api(project(":sharedComponent"))


}