package io.sophi.paywall.models

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.OSType
import io.sophi.paywall.repository.DeviceDimensionRepository

class NativeDeviceDimensionRepository : DeviceDimensionRepository {

    var deviceDimension = DeviceDimensions(
        hourOfDay = 14
    )

    override fun getAll(): DeviceDimensions {
        return deviceDimension
    }

    fun update(
        hourOfDay: Int = deviceDimension.hourOfDay,
        os: OSType? = deviceDimension.os,
        type: DeviceType? = deviceDimension.type,
        viewer: String? = deviceDimension.viewer
    ) {

        deviceDimension.hourOfDay = hourOfDay
        deviceDimension.os = os
        deviceDimension.type = type
        deviceDimension.viewer = viewer
    }

}