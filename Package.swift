// swift-tools-version:5.3

import PackageDescription

let package = Package(
    name: "Paywall",
    platforms: [
        .iOS(.v12),
    ],
    products: [
        .library(name: "Paywall", targets: ["Paywall"])
    ],
    targets: [
        .binaryTarget(
            name: "Paywall",
            url: "https://github.com/Mather-Sophi/sophi-native-apps-ios-spm-games/releases/download/v1.0.0/Paywall.xcframework.zip",
            checksum: "2929092bcc1645dd72fa3329f27ac3dcf23456d708e5f43f251d6bcdda71adfe"
        )
    ]
)
