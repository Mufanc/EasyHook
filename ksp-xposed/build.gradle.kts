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

val applicationModuleName: String by rootProject.extra

// 确保 ksp-xposed 在打包 assets 前运行
for (buildType in listOf("Debug", "Release")) {
    val filter = fun (task: Task): Boolean {
        return task.project == rootProject.project(applicationModuleName)
    }
    val kspXposed = rootProject.getTasksByName("ksp${buildType}Kotlin", true).find(filter)!!
    val mergeAssets = rootProject.getTasksByName("merge${buildType}Assets", true).find(filter)!!
    mergeAssets.dependsOn.add(kspXposed)
}
