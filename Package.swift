// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "PaywallTest",
    platforms: [
        .iOS(.v12),
    ],
    products: [
        .library(name: "PaywallTest", targets: ["PaywallTest"])
    ],
    targets: [
        // .binaryTarget(
        //     name: "Paywall",
        //     url: "https://github.com/Mather-Sophi/sophi-native-apps-ios-spm-games/releases/download/v0.0.1/Paywall.xcframework.zip",
        //     checksum: "7d005d0ccb80a9c25a923db56d1848725f540987a6decdcebeadf5f4c7a6cbf3"
        // ),
        .binaryTarget(
            name: "PaywallTest",
            path: "Sources/Paywall.xcframework" 
        )
    ]
)