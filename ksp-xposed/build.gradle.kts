plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

val androidSourceCompatibility: JavaVersion by project.parent!!.extra
val androidTargetCompatibility: JavaVersion by project.parent!!.extra

java {
    sourceCompatibility = androidSourceCompatibility
    targetCompatibility = androidTargetCompatibility
}

dependencies {
    implementation("de.robv.android.xposed:api:82")

    compileOnly("com.google.devtools.ksp:symbol-processing-api:1.7.10-1.0.6")
    implementation("com.squareup:kotlinpoet:1.12.0")
}
