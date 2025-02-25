plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android) // If using Hilt, add this too
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.wldnasyrf.ds"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.wldnasyrf.ds"
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

    buildFeatures {
        viewBinding = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit for API calls
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)

    // OkHttp for logging
    implementation(libs.logging.interceptor)

    // Room for local database
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)

    // ViewModel and LiveData (Jetpack)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Coroutines for async tasks
    implementation(libs.kotlinx.coroutines.android)

    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Glide for image loading
    implementation(libs.glide.transformations)
    implementation(libs.github.glide)
    ksp(libs.compiler)

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Gson for JSON parsing
    implementation(libs.gson)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)

    //circle image
    implementation(libs.circleimageview)
}