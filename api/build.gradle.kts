plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val androidCompileSdkVersion: Int by project.parent!!.extra
val androidMinSdkVersion: Int by project.parent!!.extra
val androidTargetSdkVersion: Int by project.parent!!.extra
val androidSourceCompatibility: JavaVersion by project.parent!!.extra
val androidTargetCompatibility: JavaVersion by project.parent!!.extra
val kotlinJvmTarget: String by project.parent!!.extra

android {
    namespace = "mufanc.easyhook.api"

    compileSdk = androidCompileSdkVersion

    defaultConfig {
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion

        consumerProguardFiles("proguard-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = androidSourceCompatibility
        targetCompatibility = androidTargetCompatibility
    }

    kotlinOptions {
        jvmTarget = kotlinJvmTarget
        freeCompilerArgs = listOf(
            "-opt-in=mufanc.easyhook.api.annotation.InternalApi"
        )
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
    api("androidx.annotation:annotation:1.4.0")
}
