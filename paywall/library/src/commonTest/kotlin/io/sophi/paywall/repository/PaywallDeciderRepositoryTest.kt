package io.sophi.paywall.repository

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import io.sophi.paywall.models.NativeDeviceDimensionRepository
import io.sophi.paywall.models.NativeUserDimensionRepository
import io.sophi.paywall.models.WallDecision
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class PaywallDeciderRepositoryTest {

    @BeforeTest
    fun setup() {
        Logger.setLogWriters(listOf(CommonWriter()))
        Logger.setMinSeverity(Severity.Debug)
    }


    // TODO: Remove this once unit tests are completed
    @Test
    fun testPaywallDecision() = runBlocking {
        val userDimensions: UserDimensionRepository = NativeUserDimensionRepository()
        val deviceDimensions: DeviceDimensionRepository = NativeDeviceDimensionRepository()
        val deciders = PaywallDeciderRepository.createNew(userDimensions, deviceDimensions)
        val thisDecider = deciders.getOneByHost(
            host = "test.sophi.io",
            settings = mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseUrl" to "http://paywall.sophi.codes",
                "configurationUrl" to "http://localhost:8080"
            )
        )

        val decision: WallDecision = thisDecider.decide(
            contentId = "408e16fc-3714-49e8-b022-a35552509ff9",
            assignedGroup = "control",
            userProperties = mapOf(
                "countryCode" to "CA"
            )
        )

        assertNotNull(decision.trace)

        assertEquals(
            "DA02AA03AB02AC01AD02AE01AF00BA07BB05BC02BD04BE03BF05BG01CA15CB10CC05CD08CE06CF12CG02", decision.context
        )
        assertContains(decision.inputs!!, "DB:America/New_York")
    }

    @Test
    fun testPaywallDeciderRepositoryUsingDimensionRepository() {
        val userDimensions = NativeUserDimensionRepository()
        val deviceDimensions = NativeDeviceDimensionRepository()
        val repository = PaywallDeciderRepository.createNew(userDimensions, deviceDimensions)

        // Using get config by host to validate the values are properly passed
        val config = repository.getPaywallDeciderConfigByHost(
            host = "www.sophi.io",
            settings = mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseURL" to "http://paywall.sophi.test"
            )
        )

        assertEquals("www.sophi.io", config.host)
        assertEquals(5000, config.settings["apiTimeoutInMilliSeconds"]?.toInt())
        assertEquals("http://paywall.sophi.test", config.settings["paywallApiBaseURL"])
        assertEquals(userDimensions.getAll(), config.userDimensions)
        assertEquals(deviceDimensions.getAll(), config.deviceDimensions)
    }

    @Test
    fun testPaywallDeciderRepositoryUsingDimensionValues() {
        val userDimensions = NativeUserDimensionRepository().getAll()
        val deviceDimensions = NativeDeviceDimensionRepository().getAll()
        val repository = PaywallDeciderRepository.createNewFromData(userDimensions, deviceDimensions)

        // Using get config by host to validate the values are properly passed
        val config = repository.getPaywallDeciderConfigByHost(
            host = "www.sophi.io",
            settings = mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseURL" to "http://paywall.sophi.test"
            )
        )

        assertEquals("www.sophi.io", config.host)
        assertEquals(5000, config.settings["apiTimeoutInMilliSeconds"]?.toInt())
        assertEquals("http://paywall.sophi.test", config.settings["paywallApiBaseURL"])
        assertEquals(userDimensions, config.userDimensions)
        assertEquals(deviceDimensions, config.deviceDimensions)
    }


}