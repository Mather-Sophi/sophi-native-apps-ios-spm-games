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
            checksum: "ff45bf5612e95f22a62443db6e75fbffde32fedb51c74d271858f630f21ef7ce"
        )
    ]
)
