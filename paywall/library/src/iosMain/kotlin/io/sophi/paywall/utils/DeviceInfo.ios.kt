package io.sophi.paywall.utils

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.OSType
import platform.UIKit.*


class IOSDeviceInfo : DeviceInfo {
    override val os: OSType
        get() = OSType.IOS
    override val deviceType: DeviceType
        get() {
            val uiInterface = UIDevice.currentDevice.userInterfaceIdiom
            return when (uiInterface) {
                UIUserInterfaceIdiomPhone -> DeviceType.MOBILE
                UIUserInterfaceIdiomPad -> DeviceType.TABLET
                UIUserInterfaceIdiomTV -> DeviceType.SMART_TV
                UIUserInterfaceIdiomMac -> DeviceType.OTHER
                UIUserInterfaceIdiomCarPlay -> DeviceType.OTHER
                UIUserInterfaceIdiomVision -> DeviceType.XR
                else -> DeviceType.UNKNOWN
            }
        }
}

actual fun getDeviceInfo(): DeviceInfo = IOSDeviceInfo()