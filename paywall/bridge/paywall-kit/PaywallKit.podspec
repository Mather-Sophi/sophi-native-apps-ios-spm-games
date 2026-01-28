require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "PaywallKit"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => min_ios_version_supported }
  s.source       = { :git => "https://github.com/Mather-Sophi/sophi-native-apps.git", :tag => "#{s.version}" }

  # Use pre-compiled XCFramework
  s.vendored_frameworks = "ios/Frameworks/paywall.xcframework"
  
  # Bridge source files to connect React Native to the framework
  s.source_files = "ios/**/*.{h,m,mm}"
  # s.exclude_files = "ios/Frameworks/**"
  s.public_header_files = "ios/**/*.h"

  install_modules_dependencies(s)
end
