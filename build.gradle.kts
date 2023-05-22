buildscript {
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}

plugins {
    id("com.android.application") version "8.2.0-alpha04" apply false
    id("com.android.library") version "8.2.0-alpha04" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}