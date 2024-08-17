// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version Versions.hiltVersion apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version Versions.secretsGradleVersion apply false
    id("com.google.gms.google-services") version Versions.googleServicesVersion apply false
    id("androidx.navigation.safeargs") version Versions.navigationVersion apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}