tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

val androidCompileSdkVersion by extra(32)
val androidMinSdkVersion by extra(27)
val androidTargetSdkVersion by extra(32)
val androidSourceCompatibility by extra(JavaVersion.VERSION_11)
val androidTargetCompatibility by extra(JavaVersion.VERSION_11)
val kotlinJvmTarget by extra("11")

val applicationModuleName by extra(":demo-app")
