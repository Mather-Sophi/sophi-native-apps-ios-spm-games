package io.sophi.paywall.models

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.OSType
import io.sophi.paywall.utils.getCurrentHour
import kotlin.test.*

class DeviceDimensionsTest {

    @Test
    fun `valid hour of day`() {
        DeviceDimensions(hourOfDay = 0)
        DeviceDimensions(hourOfDay = 23)
    }

    @Test
    fun `default dimensions created correctly without constructor inputs`() {
        val dimensions = DeviceDimensions()
        val currentHour = getCurrentHour()
        assertEquals(currentHour, dimensions.hourOfDay)
        assertEquals(DeviceType.NATIVE, dimensions.type)
        assertContains(arrayOf(OSType.IOS, OSType.ANDROID), dimensions.os)
    }

    @Test
    fun `constructor with invalid hourOfDay raises exception`() {
        val exceptionBelowRange = assertFailsWith<IllegalArgumentException> {
            DeviceDimensions(hourOfDay = -1)
        }
        assertEquals("hourOfDay must be between 0 and 23", exceptionBelowRange.message)

        val exceptionAboveRange = assertFailsWith<IllegalArgumentException> {
            DeviceDimensions(hourOfDay = 24)
        }
        assertEquals("hourOfDay must be between 0 and 23", exceptionAboveRange.message)
    }

    @Test
    fun `invalid OS type in fromMap is mapped to correct OS`() {
        val data = mapOf<String, Any?>(
            "hourOfDay" to 10,
            "os" to "invalid_os",
            "viewer" to "company-android-1.0.0"
        )

        val dimensions = DeviceDimensions.fromMap(data)
        assertEquals(10, dimensions.hourOfDay)
        assertEquals(DeviceType.NATIVE, dimensions.type)
        assertEquals("company-android-1.0.0", dimensions.viewer)
        assertNotNull(dimensions.os)
        assertContains(arrayOf(OSType.IOS, OSType.ANDROID), dimensions.os!!)
    }

    @Test
    fun `type is always native`() {
        val data = mapOf<String, Any?>(
            "hourOfDay" to 12,
            "os" to "ios",
            "type" to "web",
            "viewer" to "company-ios-2.0.0"
        )

        val dimensions = DeviceDimensions.fromMap(data)
        assertEquals(12, dimensions.hourOfDay)
        assertEquals(OSType.IOS, dimensions.os)
        assertEquals(DeviceType.NATIVE, dimensions.type) // Should always be NATIVE
    }
}

