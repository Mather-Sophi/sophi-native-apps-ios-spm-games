package io.sophi.paywall.utils

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import co.touchlab.kermit.Logger
import io.sophi.paywall.applicationContext
import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.OSType


class AndroidDeviceInfo(val context: Context) : DeviceInfo {

    override val os: OSType
        get() = OSType.ANDROID

    override val deviceType: DeviceType
        get() {
            val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager ?: run {
                Logger.w("UiModeManager not available, defaulting deviceType to 'mobile'.")
                return DeviceType.MOBILE
            }

            //TODO: Use screen size to identify tablets
            return when (uiModeManager.currentModeType) {
                Configuration.UI_MODE_TYPE_NORMAL -> DeviceType.MOBILE
                Configuration.UI_MODE_TYPE_DESK -> DeviceType.DESKTOP
                Configuration.UI_MODE_TYPE_WATCH -> DeviceType.WEARABLE
                Configuration.UI_MODE_TYPE_TELEVISION -> DeviceType.SMART_TV
                Configuration.UI_MODE_TYPE_VR_HEADSET -> DeviceType.XR
                Configuration.UI_MODE_TYPE_CAR -> DeviceType.OTHER
                Configuration.UI_MODE_TYPE_APPLIANCE -> DeviceType.EMBEDDED
                else -> DeviceType.UNKNOWN
            }
        }
}

actual fun getDeviceInfo(): DeviceInfo = AndroidDeviceInfo(applicationContext)