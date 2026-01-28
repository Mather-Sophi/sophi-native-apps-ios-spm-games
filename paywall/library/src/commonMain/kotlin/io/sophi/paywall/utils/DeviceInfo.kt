package io.sophi.paywall.utils

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.OSType

interface DeviceInfo {
    val os: OSType
    val deviceType: DeviceType
}

expect fun getDeviceInfo(): DeviceInfo