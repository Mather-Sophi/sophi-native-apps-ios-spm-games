# Paywall App SDK

![Kotlin](https://img.shields.io/badge/Kotlin_Multiplatform-2.0.21-blue.svg)

Codebase for paywall SDK with shared business logic and data models for Android and iOS platforms using Kotlin Multi Platform.

---
## Project Setup

The source code is organized as follows:
- ### library: 
  - Contains shared business logic and data models. This module generates binaries for both Android and iOS platforms.
- ### bridge:
  - ContainsReact-Native integration using TuboModules that uses binary generated from the `library` module and exposes the functionality to React-Native.
- ### example:
  - Contains platform specific integration.

## Development

### React-Native Bridge
The `bridge` module is a react-native project that contains `paywall-kit` package.
The project can be opened using any IDE that supports React-Native development (e.g. Visual Studio Code, WebStorm, etc.)
It contains Kotlin code for Android and Objective-C++ code for iOS. Since the workspace is not configured in those folders, the workspaces within the `example` module can be used to navigate and edit the code.
---
For Android, open the `bridge/paywall-kit/example/android` folder as a project in IDE. It should automatically detect the `bridge` module as a and we should be able to update `PaywallKitModule.kt` and `PaywallKitPackage.kt` under `paywall/bridge/paywall-kit/android/src/main/java/com/paywallkit`.
If there are any changes to the signature of the functions in the `PaywallKitModule.kt`, make sure to update the `PaywallKit.js` file under `bridge/paywall-kit/src` to reflect those changes.
```bash
cd bridge/paywall-kit/example/android
./gradlew generateCodegenArtifactsFromSchema
```

Known Issues:
Sometimes youi might run into issues while running gradle commands `A problem occurred starting process 'command 'node''`.
Running the following command stops all the gradle deamons and should resolve the issue:
```bash
./gradlew --stop
```

---
For iOS, open the `bridge/paywall-kit/example/ios/PaywallKitExample.xcworkspace` file in Xcode. And the Header and Objective-C++ files under Pods > Development Pods > PaywallKit will be accessible. And after every update to the xcframework, make sure to run `pod install` from the `bridge/paywall-kit/example/ios` folder to update the framework in the example project. This will ensure that the latest changes are available when updating `PaywallKit.h` and `PaywallKit.mm` files.



## Publishing
Update the version in [library/build.gradle.kts](./library/build.gradle.kts) before publishing.

### Android

To publish the Android artifact to GitHub Maven Repository, use the following Gradle command:
```bash
./gradlew publishAndroidPublicationToGitHubPackagesRepository
```
Add your GitHub Personal Access token under `gradle.properties`
```ini
# ~/.gradle/gradle.properties
gpr.user=GITHUB_USERNAME
gpr.key=GITHUB_PERSONAL_ACCESS_TOKEN
```


To publish the Android artifact to Maven Local for local testing, use the following Gradle command:

```bash
./gradlew publishAndroidPublicationToMavenLocal
```


### iOS
To build and publish the iOS framework we use CocoaPods. To publish the iOS artifact, use the following command:
```bash
./gradlew podPublishXCFramework
```
The command will build the XCFramework and save it under `library/build/cocoapods/publish/release`.


### React-Native Bridge
#### Pre-requisite: 
The `bridge` module requires the Android and iOS artifacts from the `library` module.

The iOS artifact should be generated locally and copied to `bridge/paywall-kit/ios/Frameworks`.
Run the following Gradle command to generate the iOS artifact and copy it to the above location:
```bash
./gradlew copyGeneratedIosFrameworks
```

In case of Android, you can either publish the artifact to GitHub Packages or Maven Local.
Update the `bridge/paywall-kit/android/build.gradle.kts` to point to the correct repository and dependency.

> [!WARNING]  
> Add following line for local development and testing using Maven Local. Make sure to revert these changes before publishing the package.
> ```
> repositories {
> ...
>    mavenLocal() // For Maven Local
> }
> ```
```
dependencies {
  ...
  implementation("io.sophi.android:paywall-kit:0.0.2")
}
```
