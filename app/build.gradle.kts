plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.daggerhiltandroid)
}

android {
    namespace = "com.rensystem.a06_simple_mvvm_arquitecture"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rensystem.a06_simple_mvvm_arquitecture"
        minSdk = 24
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.crrviewmodelktx)
    implementation(libs.livedataa)
    implementation(libs.fragmentt)
    implementation(libs.activitityy)

    implementation(libs.retrofit)
    implementation(libs.retrofitgson) // Convertidor Gson para Retrofit
    implementation(libs.okhttp)

    implementation(libs.daggerhiltandroid)
    kapt(libs.daggerhiltcompiler)

    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotlintest.runner)
    testImplementation(libs.mockk)
    implementation(libs.coroutiness)
    testImplementation(libs.coroutiness.test)
    testImplementation(libs.core.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}