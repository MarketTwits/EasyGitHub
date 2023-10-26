plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    viewBinding.isEnabled = true
}

dependencies {
    api(libs.fragment)
    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.constraintlayout)
    api(libs.material)
    api(libs.navigation.fragment)
    api(libs.navigation.ui.ktx)
    testApi(libs.bundles.unitTest)
    api(libs.core.splashscreen)
}