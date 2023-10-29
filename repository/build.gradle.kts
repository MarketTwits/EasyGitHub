plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.markettwits.repository"
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlin{
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
    viewBinding.enable = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":cloud-datasoruce"))
    implementation(project(":auth"))
    implementation(libs.volley)
    implementation(libs.hilt.android)
    implementation(libs.bundles.markwon)
    ksp(libs.hilt)
}