package io.sophi.paywall.models

import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.utils.getCurrentHour
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DeviceDimensionsTest {

    @Test
    fun testValidHourOfDay() {
        DeviceDimensions(hourOfDay = 0)
        DeviceDimensions(hourOfDay = 23)
    }

    @Test
    fun testDefaultDimensions() {
        val dimensions = DeviceDimensions()
        val currentHour = getCurrentHour()
        assertEquals(currentHour, dimensions.hourOfDay)
        assertEquals(DeviceType.NATIVE, dimensions.type)
    }

    @Test
    fun testInvalidHourOfDayBelowRange() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DeviceDimensions(hourOfDay = -1)
        }
        assertEquals("hourOfDay must be between 0 and 23", exception.message)
    }

    @Test
    fun testInvalidHourOfDayAboveRange() {
        val exception = assertFailsWith<IllegalArgumentException> {
            DeviceDimensions(hourOfDay = 24)
        }
        assertEquals("hourOfDay must be between 0 and 23", exception.message)
    }
}

