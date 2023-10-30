plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.markettwits.cloud_datasoruce"
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
}

dependencies {
    implementation(project(":core"))
    api(libs.bundles.hilt.worker)
    implementation(libs.hilt.android)
    implementation(libs.bundles.network)
    implementation(libs.bundles.retfrofit.serialization)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt)

}