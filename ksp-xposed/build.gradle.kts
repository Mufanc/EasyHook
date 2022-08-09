plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp") version "1.7.0-1.0.6"
}

val androidSourceCompatibility: JavaVersion by project.parent!!.extra
val androidTargetCompatibility: JavaVersion by project.parent!!.extra

java {
    sourceCompatibility = androidSourceCompatibility
    targetCompatibility = androidTargetCompatibility
}

dependencies {
//    compileOnly(project(":api-stub"))
    implementation("de.robv.android.xposed:api:82")

    compileOnly("com.google.devtools.ksp:symbol-processing-api:1.7.10-1.0.6")
    implementation("com.google.auto.service:auto-service-annotations:1.0.1")
    implementation("com.squareup:kotlinpoet:1.12.0")
    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.0.0")
}
