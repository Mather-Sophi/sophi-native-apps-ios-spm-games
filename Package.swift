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
            url: "https://github.com/Mather-Sophi/sophi-native-apps-ios-spm-games/releases/download/v0.0.1/Paywall.xcframework.zip",
            checksum: "e1427c65ff6d1bdd0b2cf0e49b13ad283f8d5f86b950e63fdf22e3df39c0643a"
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
