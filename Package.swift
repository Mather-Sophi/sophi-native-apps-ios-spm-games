// swift-tools-version: 6.2
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "PaywallKit",
    products: [
        // Products define the executables and libraries a package produces, making them visible to other packages.
        .library(
            name: "PaywallKit",
            targets: ["PaywallKit"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "PaywallBinary",
            url: "https://github.com/Mather-Sophi/sophi-native-apps-ios-spm-games/releases/download/v0.0.5/Paywall.xcframework.zip",
            checksum: "a697e81c7aaff881fa9d6bcde1aa1ff2c7cd5d4421a2d8193bd8fd2639a668a5"
        ),
        .target(
            name: "PaywallKit",
            dependencies: ["PaywallBinary"],
            path: "PaywallKit/Sources/PaywallKit"
        ),
        .testTarget(
            name: "PaywallKitTests",
            dependencies: ["PaywallKit"],
            path: "PaywallKit/Tests/PaywallKitTests"
        ),
    ]
)
