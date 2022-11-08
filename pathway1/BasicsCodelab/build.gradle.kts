buildscript {
    dependencies {
        classpath(libs.bundles.gradlePlugins)
    }
}

plugins {
    id("com.android.application") version libs.versions.androidGradlePlugin apply false
    id("com.android.library") version libs.versions.androidGradlePlugin apply false
    id("org.jetbrains.kotlin.android") version libs.versions.kotlin apply false
}
