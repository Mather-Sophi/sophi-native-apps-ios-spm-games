package io.sophi.paywall.repository

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import io.sophi.paywall.models.NativeDeviceDimensionRepository
import io.sophi.paywall.models.NativeUserDimensionRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PaywallDeciderRepositoryTest {

    @BeforeTest
    fun setup() {
        Logger.setLogWriters(listOf(CommonWriter()))
        Logger.setMinSeverity(Severity.Debug)
    }

    @Test
    fun `PaywallDecider instance is initialized correctly using dimension repositories`() {
        val userDimensions = NativeUserDimensionRepository()
        val deviceDimensions = NativeDeviceDimensionRepository()
        val repository = PaywallDeciderRepository.createNew(userDimensions, deviceDimensions)

        // Using get config by host to validate the values are properly passed
        val config = repository.getOneByHost(
            host = "www.sophi.io",
            settings = mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseURL" to "http://paywall.sophi.test"
            )
        ).config

        assertEquals("www.sophi.io", config.host)
        assertEquals(5000, config.settings["apiTimeoutInMilliSeconds"]?.toInt())
        assertEquals("http://paywall.sophi.test", config.settings["paywallApiBaseURL"])
        assertEquals(userDimensions.getAll(), config.userDimensions)
        assertEquals(deviceDimensions.getAll(), config.deviceDimensions)
    }

    @Test
    fun `PaywallDecider instance is initialized correctly using dimension values`() {
        val userDimensions = NativeUserDimensionRepository().getAll()
        val deviceDimensions = NativeDeviceDimensionRepository().getAll()
        val repository = PaywallDeciderRepository.createNewFromData(userDimensions, deviceDimensions)

        // Using get config by host to validate the values are properly passed
        val config = repository.getOneByHost(
            host = "www.sophi.io",
            settings = mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseURL" to "http://paywall.sophi.test"
            )
        ).config

        assertEquals("www.sophi.io", config.host)
        assertEquals(5000, config.settings["apiTimeoutInMilliSeconds"]?.toInt())
        assertEquals("http://paywall.sophi.test", config.settings["paywallApiBaseURL"])
        assertEquals(userDimensions, config.userDimensions)
        assertEquals(deviceDimensions, config.deviceDimensions)
    }


}