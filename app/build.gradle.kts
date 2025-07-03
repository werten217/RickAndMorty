plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.rickandmorty"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.rickandmorty"
        minSdk = 28
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation (libs.androidx.core.ktx.v1101)
    implementation (libs.androidx.lifecycle.runtime.ktx.v261)
    implementation (libs.androidx.activity.compose.v172)
    implementation (libs.ui)
    implementation (libs.material3)
    implementation (libs.androidx.navigation.compose)

    // Retrofit + Gson
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // OkHttp
    implementation (libs.logging.interceptor)
    // Coil
    implementation (libs.coil.compose)
    // Koin
    implementation (libs.koin.android)
    implementation (libs.koin.androidx.compose)

    // Coroutines
    implementation (libs.kotlinx.coroutines.core)
   // ЧЕ ЗА ХУЙНЯ НЕ РАБОТАЕТ BOTTOMBAR
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.material3.v111)
    implementation (libs.androidx.material)// Accompanist Navigation Animation
    //
    implementation ("com.google.accompanist:accompanist-navigation-animation:0.30.1")

    // Accompanist SwipeRefresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.30.1")
}