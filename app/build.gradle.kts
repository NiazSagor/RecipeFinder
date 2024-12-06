plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.recipefinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.recipefinder"
        minSdk = 27
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
            signingConfig = signingConfigs.getByName("debug")
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.navigation.compose)
    implementation(libs.dotlottie.android)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.work)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.datastore)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.androidx.splashscreen)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.room.compiler)
    implementation(libs.tikxml.annotation)
    implementation(libs.tikxml.core)
    implementation(libs.tikxml.retrofit.converter)
    kapt(libs.tikxml.processor)
    kapt(libs.tikxml.auto.value)
    compileOnly(libs.tikxml.auto.value)
}