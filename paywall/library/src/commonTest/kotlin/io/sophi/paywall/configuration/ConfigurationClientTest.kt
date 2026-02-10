package io.sophi.paywall.configuration

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import io.sophi.paywall.CONFIGURATION_API_SUCCESS_RESPONSE
import io.sophi.paywall.models.Settings
import kotlinx.serialization.json.Json
import kotlin.test.*

class ConfigurationClientTest {

    private fun getHttpClient(mockEngine: MockEngine): HttpClient {
        return HttpClient(engine = mockEngine) {
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            expectSuccess = false
        }
    }

    private lateinit var mockClient: HttpClient

    @BeforeTest
    fun setup() {
        Logger.setLogWriters(listOf(CommonWriter()))
        Logger.setMinSeverity(Severity.Debug)

        val mockEngine = MockEngine { request ->
            when {
                request.url.encodedPath == "/assets/demeter/1/stable/00000000/native.json" -> {
                    respond(
                        content = ByteReadChannel(CONFIGURATION_API_SUCCESS_RESPONSE),
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }

                request.url.encodedPath == "/assets/demeter/1/stable/3635666566/native.json" -> {
                    respond(
                        content = ByteReadChannel(CONFIGURATION_API_SUCCESS_RESPONSE),
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }

                request.url.encodedPath == "/assets/demeter/1/stable/invalid_hash/native.json" -> {
                    respond(
                        content = ByteReadChannel("""{"error": "Not Found"}"""),
                        status = HttpStatusCode.NotFound,
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }

                request.url.encodedPath == "/assets/demeter/1/stable/bad_request/native.json" -> {
                    respond(
                        content = ByteReadChannel("""{"error": "Bad Request"}"""),
                        status = HttpStatusCode.BadRequest,
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }

                else -> {
                    respond(
                        content = ByteReadChannel("""{"error": "Not Found"}"""),
                        status = HttpStatusCode.NotFound,
                        headers = headersOf("Content-Type" to listOf("text/plain"))
                    )
                }
            }
        }

        mockClient = getHttpClient(mockEngine)
    }

    @Test
    fun `getExperienceByHost returns complete configuration`() {
        val settings = Settings(
            mapOf(
                "configurationUrl" to "https://cdn.sophi.test"
            )
        )

        val configurationClient = ConfigurationClient(
            settings = settings,
            httpClient = mockClient
        )

        val experience = configurationClient.getExperienceByHost("test.sophi.io")

        // Validate basic experience structure
        assertNotNull(experience)
        assertEquals("test.sophi.io", experience.definition)
        assertNotNull(experience.policy)
        assertNotNull(experience.activeModels)
        assertTrue(experience.activeModels.isNotEmpty())

        // Validate thresholds
        assertNotNull(experience.policy.thresholds)
        assertNotNull(experience.policy.thresholds.user)
        assertNotNull(experience.policy.thresholds.user.paywall)
        assertEquals(0.3, experience.policy.thresholds.user.paywall.anonymous)
        assertEquals(0.5, experience.policy.thresholds.user.paywall.registered)
        assertEquals(0.8, experience.policy.thresholds.user.regwall)

        assertEquals(1, experience.activeModels.size)
        val model = experience.activeModels[0]
        assertEquals("on-device", model.id)
        assertNotNull(model.parameters)
        assertEquals(0.5, model.parameters.avgScore)
        assertEquals(0.1, model.parameters.avgBonus)
        assertEquals(0.0, model.parameters.avgRegwallBonus)
        assertTrue(model.parameters.isRandomized)

        val parameters = model.parameters
        assertNotNull(parameters.visitorPaywallBonusParameters)
        assertTrue(parameters.visitorPaywallBonusParameters.containsKey("anonymous"))
        assertTrue(parameters.visitorPaywallBonusParameters.containsKey("registered"))

        val anonymousParams = parameters.visitorPaywallBonusParameters["anonymous"]
        assertNotNull(anonymousParams)

        assertEquals(0.14, anonymousParams.daysSinceLastVisit?.get(3))

        assertEquals(0.16, anonymousParams.hourOfDay?.get(8))

        assertEquals(-3.0, anonymousParams.referrer?.get("search"))

        assertEquals(1.07, anonymousParams.viewer?.get("safari"))

        assertEquals(0.5, anonymousParams.visitorType?.get("anonymous"))
        assertEquals(0.2, anonymousParams.visitorType?.get("registered"))

        assertEquals(0.3, anonymousParams.sessionCampaignName?.get("SpringSale"))
        assertEquals(0.5, anonymousParams.sessionReferrerDomain?.get("example.com"))
        assertEquals(0.4, anonymousParams.sessionReferrerMedium?.get("search"))
        assertEquals(0.3, anonymousParams.sessionReferrerSource?.get("google"))
        assertEquals(0.25, anonymousParams.sessionReferrerChannel?.get("search"))

        assertEquals(0.5, anonymousParams.referrerDataMedium?.get("campaign"))
        assertEquals(0.3, anonymousParams.referrerDataSource?.get("google"))
        assertEquals(0.4, anonymousParams.referrerDataChannel?.get("search"))
        assertEquals(0.15, anonymousParams.referrerDataChannel?.get("news"))

        assertEquals(-0.5, anonymousParams.os?.get("android"))
        assertEquals(0.3, anonymousParams.os?.get("ios"))

        assertEquals(-0.4, anonymousParams.type?.get("mobile"))
        assertEquals(0.2, anonymousParams.type?.get("tablet"))

        assertEquals(0.5, anonymousParams.todayPageViews?.get(0))
        assertEquals(0.1, anonymousParams.todayPageViews?.get(1))
        assertEquals(0.65, anonymousParams.todayPageViewsByArticle?.get(0))
        assertEquals(-0.26, anonymousParams.todayPageViewsByArticle?.get(1))
        assertEquals(0.34, anonymousParams.todayPageViewsByArticle?.get(2))
        assertEquals(0.71, anonymousParams.todayPageViewsByArticle?.get(3))
        assertEquals(-0.18, anonymousParams.todayTopLevelSections?.get(1))
        assertEquals(0.61, anonymousParams.todayTopLevelSections?.get(2))
        assertEquals(0.73, anonymousParams.todayTopLevelSections?.get(3))
        assertEquals(0.68, anonymousParams.todayStopRate?.get(0.0))
        assertEquals(4.43, anonymousParams.todayStopRate?.get(0.1))
        assertEquals(-0.14, anonymousParams.todayStopRate?.get(1.0))

        assertEquals(0.5, anonymousParams.sevenDayPageViews?.get(0))
        assertEquals(0.1, anonymousParams.sevenDayPageViews?.get(1))
        assertEquals(0.41, anonymousParams.sevenDayPageViewsByArticle?.get(0))
        assertEquals(-0.07, anonymousParams.sevenDayPageViewsByArticle?.get(1))
        assertEquals(-0.41, anonymousParams.sevenDayPageViewsByArticle?.get(2))
        assertEquals(0.5, anonymousParams.sevenDayVisitCount?.get(0))
        assertEquals(0.1, anonymousParams.sevenDayVisitCount?.get(1))
        assertEquals(0.33, anonymousParams.sevenDayStopRate?.get(0.0))
        assertEquals(0.69, anonymousParams.sevenDayStopRate?.get(0.1))
        assertEquals(-0.33, anonymousParams.sevenDayStopRate?.get(1.0))

        assertEquals(0.5, anonymousParams.twentyEightDayPageViews?.get(0))
        assertEquals(0.1, anonymousParams.twentyEightDayPageViews?.get(1))
        assertEquals(0.5, anonymousParams.twentyEightDayPageViewsByArticle?.get(0))
        assertEquals(0.1, anonymousParams.twentyEightDayPageViewsByArticle?.get(1))
        assertEquals(0.5, anonymousParams.twentyEightDayVisitCount?.get(0))
        assertEquals(0.1, anonymousParams.twentyEightDayVisitCount?.get(1))

        val registeredParams = parameters.visitorPaywallBonusParameters["registered"]
        assertNotNull(registeredParams)
        assertEquals(0.57, registeredParams.referrer?.get("campaign"))
        assertEquals(-0.02, registeredParams.referrer?.get("direct"))
        assertEquals(4.72, registeredParams.referrer?.get("internal"))
        assertEquals(0.01, registeredParams.daysSinceLastVisit?.get(0))
        assertEquals(0.28, registeredParams.daysSinceLastVisit?.get(1))

        val regwallParams = model.parameters.regwallBonusParameters
        assertNotNull(regwallParams)

        assertEquals(0.05, regwallParams.daysSinceLastVisit?.get(0))
        assertEquals(-0.31, regwallParams.daysSinceLastVisit?.get(1))
        assertEquals(-0.44, regwallParams.daysSinceLastVisit?.get(2))
        assertEquals(-0.21, regwallParams.daysSinceLastVisit?.get(3))

        assertEquals(-0.44, regwallParams.hourOfDay?.get(0))
        assertEquals(-0.12, regwallParams.hourOfDay?.get(2))
        assertEquals(0.25, regwallParams.hourOfDay?.get(10))
        assertEquals(0.29, regwallParams.hourOfDay?.get(12))

        assertEquals(1.07, regwallParams.referrer?.get("campaign"))
        assertEquals(0.29, regwallParams.referrer?.get("direct"))
        assertEquals(3.31, regwallParams.referrer?.get("internal"))
        assertEquals(-0.7, regwallParams.referrer?.get("search"))

        assertEquals(0.67, regwallParams.viewer?.get("android webview"))
        assertEquals(-0.31, regwallParams.viewer?.get("chrome"))
        assertEquals(1.93, regwallParams.viewer?.get("edge"))
        assertEquals(0.88, regwallParams.viewer?.get("firefox"))

        assertEquals(0.28, regwallParams.sevenDayStopRate?.get(0.0))
        assertEquals(-0.65, regwallParams.sevenDayStopRate?.get(0.1))
        assertEquals(-0.6, regwallParams.sevenDayStopRate?.get(1.0))
        assertEquals(0.05, regwallParams.todayStopRate?.get(0.0))
        assertEquals(-0.06, regwallParams.todayStopRate?.get(0.2))
        assertEquals(3.19, regwallParams.todayStopRate?.get(1.0))

        assertNotNull(experience.propertyCodes)
        assertNotNull(experience.propertyCodes.user)
        assertNotNull(experience.propertyCodes.content)
        assertEquals(2, experience.propertyCodes.user.size)
        assertEquals(1, experience.propertyCodes.content.size)

        assertEquals("countryCode", experience.propertyCodes.user[0].key)
        assertEquals("countryCode", experience.propertyCodes.user[0].name)
        assertEquals("string", experience.propertyCodes.user[0].type)
        assertEquals("2", experience.propertyCodes.user[1].key)
        assertEquals("dummyField", experience.propertyCodes.user[1].name)
        assertEquals("string", experience.propertyCodes.user[1].type)

        assertEquals("1", experience.propertyCodes.content[0].key)
        assertEquals("section", experience.propertyCodes.content[0].name)
        assertEquals("string", experience.propertyCodes.content[0].type)

        val foundModel = experience.getModelById("on-device")
        assertNotNull(foundModel)
        assertEquals("on-device", foundModel.id)

        val notFoundModel = experience.getModelById("non-existent-model")
        assertNull(notFoundModel)
    }

    @Test
    fun `getExperienceByHost with unsupported host returns fallback experience`() {
        val settings = Settings(
            mapOf(
                "configurationUrl" to "https://cdn.sophi.test"
            )
        )

        val configurationClient = ConfigurationClient(
            settings = settings,
            httpClient = mockClient
        )

        val experience = configurationClient.getExperienceByHost("www.unsupported-domain.com")

        // Should return fallback experience when host is not configured
        assertNotNull(experience)
        assertEquals("fallback-experience", experience.definition)
        assertNotNull(experience.policy)
        assertNotNull(experience.activeModels)
    }
}
