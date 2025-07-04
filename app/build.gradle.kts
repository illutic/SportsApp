import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "g.sig.sportsapp"
    compileSdk = properties["g.sig.sportsapp.compileSdk"].toString().toInt()

    defaultConfig {
        applicationId = "g.sig.sportsapp"
        minSdk = properties["g.sig.sportsapp.minSdk"].toString().toInt()
        targetSdk = properties["g.sig.sportsapp.targetSdk"].toString().toInt()
        versionCode = properties["g.sig.sportsapp.versionCode"].toString().toInt()
        versionName = properties["g.sig.sportsapp.versionName"].toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.activity.compose)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
}

private val Provider<String>.intOrNull get(): Int? = orNull?.toIntOrNull()
