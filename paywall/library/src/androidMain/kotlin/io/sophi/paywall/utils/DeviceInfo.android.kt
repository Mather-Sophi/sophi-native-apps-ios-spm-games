package io.sophi.paywall.utils

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.OSType


class AndroidDeviceInfo() : DeviceInfo {

    override val os: OSType
        get() = OSType.ANDROID

    override val deviceType: DeviceType
        get() = DeviceType.NATIVE
}

actual fun getDeviceInfo(): DeviceInfo = AndroidDeviceInfo()