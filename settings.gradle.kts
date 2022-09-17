pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.library") version "7.3.0" apply false
        id("org.jetbrains.kotlin.android") version "1.7.10" apply false
        id("com.android.application") version "7.3.0"
        id("org.jetbrains.kotlin.jvm") version "1.7.10"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://api.xposed.info/")
    }
}

rootProject.name = "EasyHook"
include(":api", ":ksp-xposed")
include(":demo-app")
