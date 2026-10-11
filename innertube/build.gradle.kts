plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.veritm.innertube"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // PERBAIKAN: Paksa varian androidJvm untuk innertubex
    // JitPack mempublikasikan innertubex sebagai KMP dengan banyak varian
    // (iosArm64, iosSimulatorArm64, jvm, dll.) sehingga Gradle bingung memilih.
    // Solusi: tambahkan atribut resolusi eksplisit pada dependency.
    if (providers.gradleProperty("useMavenLocalInnerTubeX").isPresent) {
        api("com.github.MetrolistGroup:innertubex:${libs.versions.innertubex.get()}") {
            attributes {
                attribute(
                    Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java),
                    "androidJvm"
                )
            }
        }
    } else {
        api(libs.innertubex) {
            attributes {
                attribute(
                    Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java),
                    "androidJvm"
                )
            }
        }
    }
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.encoding)
    implementation(libs.timber)
    testImplementation(libs.junit)
    testImplementation(libs.ktor.client.mock)

    coreLibraryDesugaring(libs.desugaring)
}

// PERBAIKAN TAMBAHAN: Pastikan semua konfigurasi resolusi menggunakan androidJvm
configurations.all {
    attributes {
        attribute(
            Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java),
            "androidJvm"
        )
    }
}
