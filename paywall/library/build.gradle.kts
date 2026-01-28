import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.cocoapods)

    id("maven-publish")
}


val artifactName = "paywall"
version="0.0.4-alpha.1"
group="io.sophi"

kotlin {



    androidLibrary {

        group = "$group.android"
        namespace = "$group.$artifactName"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {
            isIncludeAndroidResources = true
        }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_22)
                }
            }
        }
    }

    iosX64() // Apple iOS simulator on x86-64 platforms
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version
        summary = "Paywall SDK for iOS"
        homepage = "https://github.com/Mather-Sophi/sophi-native-apps"
        framework {
            baseName = "Paywall"
        }
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE

    }


    tasks.register<Copy>("copyGeneratedIosFrameworkToPaywallKit"){
        description = "Copies the generated iOS frameworks to the react-native bridge directory."
        dependsOn("podPublishXCFramework")
        from("build/cocoapods/publish/release/")
        into("../bridge/paywall-kit/ios/Frameworks")
    }

    tasks.register<Copy>("copyGeneratedIosFrameworkToPaywallKitLegacy"){
        description = "Copies the generated iOS frameworks to the legacy react-native bridge directory."
        dependsOn("podPublishXCFramework")
        from("build/cocoapods/publish/release/")
        into("../bridge/paywall-kit-legacy/ios/Frameworks")
    }

    tasks.register<Copy>("copyGeneratedIosFramework") {
        description = "Copies the generated iOS frameworks to the react-native bridge directories."
        dependsOn(arrayOf("copyGeneratedIosFrameworkToPaywallKitLegacy", "copyGeneratedIosFrameworkToPaywallKit"))
    }

    sourceSets {
        commonMain{
            dependencies {
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.contentnegotiation)
                implementation(libs.ktor.serialization.json)
                implementation(libs.touchlab.kermit)
                implementation(libs.lifecycle.runtime.compose)
                implementation(libs.okio)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.okio.fakefilesystem) // TODO: Remove if not saving cache to fs
        }

        androidMain.dependencies {
            implementation(libs.javascriptengine)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.android.startup.runtime)
        }

        getByName("androidHostTest").dependencies {
            implementation("androidx.test:core:1.7.0")
            implementation("org.robolectric:robolectric:4.16")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }
 }

publishing {
    repositories {
//        maven {
//            name = "GitHubPackages"
//            url = uri("https://maven.pkg.github.com/Mather-Sophi/io-sophi-paywall-native")
//            credentials {
//                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
//                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
//            }
//        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["kotlin"])
        }

        withType<MavenPublication>{
            artifactId = artifactId.replace("-android", "-kit")
        }
    }
}