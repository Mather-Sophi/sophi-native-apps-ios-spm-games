package io.sophi.paywall.models

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.utils.getCurrentHour
import io.sophi.paywall.utils.getDeviceInfo
import kotlinx.serialization.Serializable

/**
 * Class representing device dimensions for analytics or tracking purposes.
 *
 * @property hourOfDay The hour of the day (0-23).
 * @property os The operating system of the device (e.g., "iOS", "Android").
 * @property type The type of device ("native").
 * @property viewer The release version of the viewer application (e.g., "company-android-1.0.0").
 */


@Serializable
data class DeviceDimensions(
    var hourOfDay: Int = getCurrentHour(),
    var os: String? = null,
    var type: DeviceType? = DeviceType.NATIVE,
    var viewer: String? = null
) {
    init {
        val deviceInfo = getDeviceInfo()
        require(hourOfDay in 0..23) { "hourOfDay must be between 0 and 23" }

        if (os == null) {
            os = deviceInfo.os.value
        }

        // Hardcode Device Type to Native
        type = DeviceType.NATIVE
    }

    companion object Factory {
        fun fromMap(data: Map<String, Any?>): DeviceDimensions {
            return DeviceDimensions(
                hourOfDay = (data["hourOfDay"] as? Number)?.toInt() ?: getCurrentHour(),
                os = data["os"] as? String,
                type = DeviceType.NATIVE,
                viewer = data["viewer"] as? String
            )
        }
    }
}

