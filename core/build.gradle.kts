import kotlinx.coroutines.test.TestCoroutineContext

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.markettwits.core"
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
    viewBinding.isEnabled = true
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt)
    implementation(libs.navigation.ui.ktx)
    api(libs.fragment)
    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.constraintlayout)
    api(libs.material)
    testApi(libs.bundles.unitTest)
    androidTestApi(libs.testng)
    api(libs.junit.ktx)
    api(libs.core.splashscreen)
}
