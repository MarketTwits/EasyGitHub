// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
}
buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${libs.navigation.ui.ktx.get().version}")
    }
}