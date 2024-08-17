plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "br.thiago.moviemdb"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.thiago.moviemdb"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}")

    // OKHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:${Versions.okhttpVersion}"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Splash API
    implementation("androidx.core:core-splashscreen:${Versions.splashVersion}")

    // Lottie
    implementation("com.airbnb.android:lottie:${Versions.lottieVersion}")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.2.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    ksp("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}")

    // Glide
    implementation("com.github.bumptech.glide:glide:${Versions.glideVersion}")
    ksp("com.github.bumptech.glide:ksp:${Versions.glideVersion}")

    // SimpleSearchView
    implementation("com.github.Ferfalk:SimpleSearchView:0.2.0")

    // Room
    implementation("androidx.room:room-ktx:${Versions.roomVersion}")
    implementation("androidx.room:room-runtime:${Versions.roomVersion}")
    ksp("androidx.room:room-compiler:${Versions.roomVersion}")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:${Versions.pagingVersion}")

    // Shimmer
    implementation("com.facebook.shimmer:shimmer:${Versions.shimmerVersion}")

    // Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeVersion}")

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}