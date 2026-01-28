import org.gradle.kotlin.dsl.maven

rootProject.name = "app"

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }

        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.9.0")
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
//        maven {
//            name = "GitHubPackages"
//            url = uri("https://maven.pkg.github.com/Mather-Sophi/io-sophi-paywall-native")
//            credentials {
//                username = providers.gradleProperty("gpr.user").getOrElse(System.getenv("USERNAME"))
//                password = providers.gradleProperty("gpr.key").getOrElse(System.getenv("TOKEN"))
//            }
//        }
        mavenLocal()
    }
}
include(":app")
