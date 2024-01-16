import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "An multiplatform interoperable datetime library"

kotlin {
    jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasmJs { library() }
    if (Targeting.OSX) osxTargets()
    if (Targeting.NDK) ndkTargets()
    if (Targeting.LINUX) linuxTargets()
    if (Targeting.MINGW) mingwTargets()

    sourceSets {
        commonMain.dependencies {
            api(kotlinx.serialization.core)
            api(libs.kase.core)
        }

        commonTest.dependencies {
            implementation(libs.kommander.core)
            implementation(kotlinx.serialization.json)
        }
    }
}

rootProject.the<NodeJsRootExtension>().apply {
    nodeVersion = npm.versions.node.version.get()
    nodeDownloadBaseUrl = npm.versions.node.url.get()
}

rootProject.tasks.withType<KotlinNpmInstallTask>().configureEach {
    args.add("--ignore-engines")
}

tasks.named("wasmJsTestTestDevelopmentExecutableCompileSync").configure {
    mustRunAfter(tasks.named("jsBrowserTest"))
    mustRunAfter(tasks.named("jsNodeTest"))
}