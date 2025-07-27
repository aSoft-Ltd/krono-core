plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "An implementation of the krono.api based on kotlinx"

kotlin {
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasmJs { library() } // Somehow tests are failing when trying to load @js-joda/core
    if (Targeting.OSX) osxTargets()
//    if (Targeting.NDK) ndkTargets()
    if (Targeting.LINUX) linuxTargets()
    if (Targeting.MINGW) mingwTargets()

    sourceSets {
        commonMain.dependencies {
            api(projects.kronoApi)
            api(kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.kommander.coroutines)
            implementation(kotlinx.serialization.json)
        }

        if (Targeting.JVM) jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
        }
    }
}