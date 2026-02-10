package io.sophi.paywall.repository

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
import io.sophi.paywall.PAYWALL_API_NOWALL_RESPONSE
import io.sophi.paywall.PAYWALL_API_PAYWALL_RESPONSE
import io.sophi.paywall.enums.ReferrerMedium
import io.sophi.paywall.enums.VisitorType
import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.NativeDeviceDimensionRepository
import io.sophi.paywall.models.NativeUserDimensionRepository
import io.sophi.paywall.models.Outcome
import io.sophi.paywall.models.Settings
import io.sophi.paywall.utils.getDeviceInfo
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PaywallDeciderTest {

    private fun getHttpClient(mockEngine: MockEngine): HttpClient {
        return HttpClient(engine = mockEngine) {
            install(HttpTimeout) {
                requestTimeoutMillis = 1000
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            expectSuccess = true
        }
    }

    private lateinit var mockClient: HttpClient
    val os = getDeviceInfo().os.value

    @BeforeTest
    fun setup() {
        Logger.setLogWriters(listOf(CommonWriter()))
        Logger.setMinSeverity(Severity.Debug)

        val mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/hosts/test.sophi.io/decisions/me" -> {
                    when (request.url.parameters.get("content.id")) {
                        "no-wall-content-id" -> {
                            respond(
                                content = ByteReadChannel(PAYWALL_API_NOWALL_RESPONSE),
                                status = HttpStatusCode.OK,
                                headers = headersOf("Content-Type" to listOf("application/json"))
                            )
                        }

                        "unsupported-host-content-id" -> {
                            respond(
                                content = ByteReadChannel("""{"error": "Host www.example.com is not supported"}"""),
                                status = HttpStatusCode.NotFound,
                                headers = headersOf("Content-Type" to listOf("application/json"))
                            )
                        }

                        "bad-request-content-id" -> {
                            respond(
                                content = ByteReadChannel("""{"error": "Bad Request"}"""),
                                status = HttpStatusCode.BadRequest,
                                headers = headersOf("Content-Type" to listOf("application/json"))
                            )
                        }

                        else -> {
                            respond(
                                content = ByteReadChannel(PAYWALL_API_PAYWALL_RESPONSE),
                                status = HttpStatusCode.OK,
                                headers = headersOf("Content-Type" to listOf("application/json"))
                            )
                        }
                    }
                }

                "/assets/demeter/1/stable/00000000/native.json" -> {
                    respond(
                        content = ByteReadChannel(CONFIGURATION_API_SUCCESS_RESPONSE),
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }

                else -> {
                    respond(
                        content = ByteReadChannel("Not Found"),
                        status = HttpStatusCode.NotFound,
                        headers = headersOf("Content-Type" to listOf("text/plain"))
                    )
                }

            }
        }

        mockClient = getHttpClient(mockEngine)

    }

    @Test
    fun `test decision when API returns paywall`() {
        val settings = Settings(
            mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseUrl" to "https://paywall.sophi.test",
                "configurationUrl" to "https://cdn.sophi.test"
            )
        )

        val decider = PaywallDecider(
            host = "test.sophi.io",
            settings = settings,
            userDimensionRepository = NativeUserDimensionRepository(),
            deviceDimensionRepository = NativeDeviceDimensionRepository(),
            httpClient = mockClient
        )

        runBlocking {
            val decision = decider.decide(
                contentId = "paywall-content-id",
                assignedGroup = "variant",
                userProperties = mapOf(
                    "countryCode" to "US"
                )
            )

            // id matches Paywall API response
            assertEquals("4df3ad44-77f3-4fce-9da6-80d897fcd091", decision.id)
            assertEquals("2026-01-16T19:06:48", decision.createdAt)
            assertEquals("z114450e114450v000050", decision.trace)
            assertEquals(
                "DA02AA03AB02AC01AD02AE01AF00BA07BB05BC02BD04BE03BF05BG01CA15CB10CC05CD08CE06CF12CG02",
                decision.context
            )
            assertEquals(
                "HB:02|FA:01|DC:03|DE:03|DF:08|DG:00|EA:${os}|EB:06|DD:14|DB:America/New_York|GA:SpringSale|GB:google.com|GC:search|GD:google|GE:search",
                decision.inputs
            )
            assertEquals(Outcome(wallType = WallType.PAYWALL, wallVisibility = WallVisibility.ALWAYS), decision.outcome)
        }
    }

    @Test
    fun `test decision when API returns no wall`() {
        val settings = Settings(
            mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseUrl" to "https://paywall.sophi.test",
                "configurationUrl" to "https://cdn.sophi.test"
            )
        )

        val decider = PaywallDecider(
            host = "test.sophi.io",
            settings = settings,
            userDimensionRepository = NativeUserDimensionRepository(),
            deviceDimensionRepository = NativeDeviceDimensionRepository(),
            httpClient = mockClient
        )

        runBlocking {
            val decision = decider.decide(
                contentId = "no-wall-content-id",
                userProperties = mapOf(
                    "countryCode" to "US"
                )
            )

            // id matches Paywall API response
            assertEquals("f1426252-8561-4d5c-a086-9d8985bdf81e", decision.id)
            assertEquals("2026-01-16T18:50:29", decision.createdAt)
            assertEquals("z006550e006550v000050", decision.trace)
            assertEquals(
                "DA02AA03AB02AC01AD02AE01AF00BA07BB05BC02BD04BE03BF05BG01CA15CB10CC05CD08CE06CF12CG02",
                decision.context
            )
            assertEquals(
                "FA:01|DC:03|DE:03|DF:08|DG:00|EA:${os}|EB:06|DD:14|DB:America/New_York|GA:SpringSale|GB:google.com|GC:search|GD:google|GE:search",
                decision.inputs
            )
            assertEquals(Outcome(wallVisibility = WallVisibility.NEVER), decision.outcome)

            assertEquals("countryCode:US", decision.userProperties)
            assertNull(decision.contentProperties)
            assertNull(decision.experimentsCode)
            assertNull(decision.paywallScore)
        }
    }

    @Test
    fun `test decision when API returns error`() {
        val settings = Settings(
            mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseUrl" to "https://paywall.sophi.test",
                "configurationUrl" to "https://cdn.sophi.test"
            )
        )

        val userDimensionRepository = NativeUserDimensionRepository()
        val deviceDimensionRepository = NativeDeviceDimensionRepository()

        val decider = PaywallDecider(
            host = "test.sophi.io",
            settings = settings,
            userDimensionRepository = userDimensionRepository,
            deviceDimensionRepository = deviceDimensionRepository,
            httpClient = mockClient
        )

        runBlocking {
            val decision = decider.decide(
                contentId = "no-wall-content-id",
                assignedGroup = "control",
                userProperties = mapOf(
                    "countryCode" to "US"
                )
            )

            // id matches Paywall API response
            assertEquals("f1426252-8561-4d5c-a086-9d8985bdf81e", decision.id)
            assertEquals("2026-01-16T18:50:29", decision.createdAt)
            assertEquals("z006550e006550v000050", decision.trace)
            assertEquals(
                "DA02AA03AB02AC01AD02AE01AF00BA07BB05BC02BD04BE03BF05BG01CA15CB10CC05CD08CE06CF12CG02",
                decision.context
            )
            assertEquals(
                "HB:01|FA:01|DC:03|DE:03|DF:08|DG:00|EA:${os}|EB:06|DD:14|DB:America/New_York|GA:SpringSale|GB:google.com|GC:search|GD:google|GE:search",
                decision.inputs
            )
            assertEquals(Outcome(wallVisibility = WallVisibility.NEVER), decision.outcome)

            assertEquals("countryCode:US", decision.userProperties)
            assertNull(decision.contentProperties)
            assertNull(decision.experimentsCode)
            assertNull(decision.paywallScore)
        }


    }

    @Test
    fun `test decision updated after user and device dimension values are updated`() {
        val settings = Settings(
            mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseUrl" to "https://paywall.sophi.test",
                "configurationUrl" to "https://cdn.sophi.test"
            )
        )

        val userDimensionRepository = NativeUserDimensionRepository()
        val deviceDimensionRepository = NativeDeviceDimensionRepository()

        val decider = PaywallDecider(
            host = "test.sophi.io",
            settings = settings,
            userDimensionRepository = userDimensionRepository,
            deviceDimensionRepository = deviceDimensionRepository,
            httpClient = mockClient
        )

        runBlocking {
            val decision = decider.decide(
                contentId = "no-wall-content-id",
            )

            // id matches Paywall API response
            assertEquals("f1426252-8561-4d5c-a086-9d8985bdf81e", decision.id)
            assertEquals("2026-01-16T18:50:29", decision.createdAt)
            assertEquals("z006550e006550v000050", decision.trace)
            assertEquals(
                "DA02AA03AB02AC01AD02AE01AF00BA07BB05BC02BD04BE03BF05BG01CA15CB10CC05CD08CE06CF12CG02",
                decision.context
            )
            assertEquals(
                "FA:01|DC:03|DE:03|DF:08|DG:00|EA:${os}|EB:06|DD:14|DB:America/New_York|GA:SpringSale|GB:google.com|GC:search|GD:google|GE:search",
                decision.inputs
            )
            assertEquals(Outcome(wallVisibility = WallVisibility.NEVER), decision.outcome)
        }

        userDimensionRepository.update(
            todayPageViews = 5,
            sevenDayPageViewsByArticleWithPaywall = 10,
            visitorType = VisitorType.ANONYMOUS,
            referrer = ReferrerMedium.INTERNAL
        )

        deviceDimensionRepository.update(
            hourOfDay = 9
        )

        runBlocking {
            val decision = decider.decide(
                contentId = "no-wall-content-id",
                userProperties = mapOf(
                    "countryCode" to "US",
                    "dummyField" to "test"
                ),
                contentProperties = mapOf(
                    "section" to "sports"
                )
            )

            // id matches Paywall API response
            assertEquals("f1426252-8561-4d5c-a086-9d8985bdf81e", decision.id)
            assertEquals("2026-01-16T18:50:29", decision.createdAt)
            assertEquals("z006550e006550v000050", decision.trace)
            assertEquals(
                "DA02AA05AB02AC01AD02AE01AF00BA07BB05BC10BD04BE03BF05BG01CA15CB10CC05CD08CE06CF12CG02",
                decision.context
            )
            assertEquals(
                "FA:00|DC:02|DE:03|DF:08|DG:00|EA:${os}|EB:06|DD:09|DB:America/New_York|GA:SpringSale|GB:google.com|GC:search|GD:google|GE:search",
                decision.inputs
            )
            assertEquals(Outcome(wallVisibility = WallVisibility.NEVER), decision.outcome)

            assertEquals("countryCode:US|2:test", decision.userProperties)
            assertEquals("1:sports", decision.contentProperties)
            assertNull(decision.experimentsCode)
            assertNull(decision.paywallScore)
        }
    }

    @Test
    fun `updateDimensions updates dimensions`() {
        val settings = Settings(
            mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiBaseUrl" to "https://paywall.sophi.test",
                "configurationUrl" to "https://cdn.sophi.test"
            )
        )

        val decider = PaywallDecider(
            host = "test.sophi.io",
            settings = settings,
            userDimensionRepository = NativeUserDimensionRepository(),
            deviceDimensionRepository = NativeDeviceDimensionRepository(),
            httpClient = mockClient
        )

        val newUserDimensions = decider.userDimensionRepository.getAll()
        val newDeviceDimensions = decider.deviceDimensionRepository.getAll()

        newUserDimensions.visitorType = VisitorType.ANONYMOUS
        newUserDimensions.sevenDayPageViewsByArticleWithPaywall = 10

        newDeviceDimensions.hourOfDay = 5

        decider.updateDimensions(
            userDimensions = newUserDimensions,
            deviceDimensions = newDeviceDimensions
        )

        val config = decider.config


        assertEquals(VisitorType.ANONYMOUS, config.userDimensions.visitorType)
        assertEquals(10, config.userDimensions.sevenDayPageViewsByArticleWithPaywall)
        assertEquals(5, config.deviceDimensions.hourOfDay)
    }
}