plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "ru.appcommerce.mapkittaxi"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.appcommerce.mapkittaxi"
        minSdk = 26
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.coroutinesAndroid)
    implementation(libs.fragmentKtx)

    implementation(libs.yandex.map.kit)
    implementation(libs.cicerone)
    implementation(libs.koin)
    implementation(libs.koinAndroid)
    implementation(libs.koinCompat)
    implementation(libs.easypermissions)
    implementation(libs.viewBindingDelegate)
    implementation(libs.jsr)
}