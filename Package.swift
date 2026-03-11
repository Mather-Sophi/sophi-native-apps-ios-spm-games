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
            url: "https://github.com/Mather-Sophi/sophi-native-apps-ios-spm-games/releases/download/v0.0.1/Paywall.xcframework.zip",
            checksum: "e1427c65ff6d1bdd0b2cf0e49b13ad283f8d5f86b950e63fdf22e3df39c0643a"
        )
    ]
)
