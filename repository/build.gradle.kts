plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    viewBinding.enable = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":cloud-datasoruce"))
    implementation(project(":auth"))
    implementation(libs.junit.ktx)
    implementation(libs.volley)
    testImplementation("org.testng:testng:6.9.6")
    androidTestImplementation("org.testng:testng:6.9.6")
    ksp(libs.hilt)
    implementation(libs.hilt.android)
    implementation("io.noties.markwon:core:4.6.2")
    implementation ("io.noties.markwon:linkify:4.6.2")
    implementation("io.noties.markwon:image-glide:4.6.2")


}