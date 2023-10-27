plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version libs.versions.kotlin
    id("dagger.hilt.android.plugin")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    val work_version = "2.8.1"
    // Kotlin + coroutines
    api("androidx.work:work-runtime-ktx:$work_version")
    ksp("androidx.hilt:hilt-compiler:1.1.0-alpha01")
    api(libs.hilt.work)
    implementation(project(":core"))
    testImplementation("org.testng:testng:6.9.6")
    ksp(libs.hilt)
    implementation(libs.hilt.android)
    implementation(libs.bundles.network)
    implementation(libs.bundles.retfrofit.serialization)

}