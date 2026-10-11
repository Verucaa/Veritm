@file:Suppress("UnstableApiUsage")

val useMavenLocalInnerTubeX = providers.gradleProperty("useMavenLocalInnerTubeX").isPresent

pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        exclusiveContent {
            forRepository {
                if (useMavenLocalInnerTubeX) mavenLocal() else maven("https://jitpack.io")
            }
            filter {
                if (useMavenLocalInnerTubeX) {
                    includeModule("com.github.MetrolistGroup", "innertubex")
                    includeModule("com.github.MetrolistGroup", "innertubex-android")
                    includeModule("com.github.MetrolistGroup", "innertubex-desktop")
                } else {
                    includeGroup("com.github.MetrolistGroup.innertubex")
                }
            }
        }
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
    }

    // PERBAIKAN: Paksa resolusi varian androidJvm untuk dependensi KMP
    // Ini diperlukan karena JitPack mempublikasikan banyak varian (native, jvm, dll.)
    // dan Gradle tidak bisa memilih varian yang tepat secara otomatis.
    resolutionStrategy.dependencySubstitution {
        // Tidak ada substitusi, kita hanya menambahkan atribut resolusi
    }

    // Strategi resolusi untuk semua konfigurasi
    configurations.all {
        attributes {
            // Paksa platform type ke androidJvm untuk semua resolusi dependensi
            // Ini memastikan Gradle memilih varian androidJvm dari innertubex
            attribute(
                Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java),
                "androidJvm"
            )
        }
    }
}

rootProject.name = "VERITM"
include(":app")
include(":innertube")
