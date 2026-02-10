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
            path: "build/cocoapods/publish/release/Paywall.xcframework"
        )
    ]
)
