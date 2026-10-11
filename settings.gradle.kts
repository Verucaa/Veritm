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
                if (useMavenLocalInnerTubeX) {
                    mavenLocal()
                } else {
                    // PERBAIKAN: Abaikan file .module dari JitPack yang rusak.
                    // JitPack mempublikasikan innertubex sebagai KMP dengan metadata
                    // Gradle Module Metadata yang tidak lengkap, sehingga Gradle
                    // memilih varian iosArm64/iosSimulatorArm64 alih-alih androidJvm.
                    // Dengan metadataSources { mavenPom(); artifact() }, kita
                    // memaksa resolusi gaya Maven klasik (POM + AAR).
                    maven {
                        url = uri("https://jitpack.io")
                        metadataSources {
                            mavenPom()
                            artifact()
                            ignoreGradleMetadataRedirection()
                        }
                    }
                }
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
}

// F-Droid doesn't support foojay-resolver plugin
rootProject.name = "VERITM"
include(":app")
include(":innertube")
