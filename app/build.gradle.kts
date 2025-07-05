import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
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
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.ui)

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.activity.compose)

    // Async
    implementation(libs.kotlinx.coroutines)

    // Dependency Injection
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.io.mockk)
}
