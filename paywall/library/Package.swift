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
            url: "https://github.com/Mather-Sophi/sophi-native-apps-ios-spm-games/releases/download/${RELEASE_TAG}/Paywall.xcframework.zip",
            checksum: "${CHECKSUM}"
        )
    ]
)
