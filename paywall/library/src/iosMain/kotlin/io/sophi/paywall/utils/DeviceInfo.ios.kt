package io.sophi.paywall.utils

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.OSType


class IOSDeviceInfo : DeviceInfo {
    override val os: OSType
        get() = OSType.IOS
    override val deviceType: DeviceType
        get() = DeviceType.NATIVE
}

actual fun getDeviceInfo(): DeviceInfo = IOSDeviceInfo()