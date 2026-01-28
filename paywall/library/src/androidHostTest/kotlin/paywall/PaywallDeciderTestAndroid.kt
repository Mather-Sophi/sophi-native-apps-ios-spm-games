package io.sophi.paywall


import androidx.test.core.app.ApplicationProvider
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import io.sophi.paywall.enums.ReferrerChannel
import io.sophi.paywall.enums.ReferrerMedium
import io.sophi.paywall.enums.ReferrerSource
import io.sophi.paywall.enums.VisitorType
import io.sophi.paywall.models.DeviceDimensions
import io.sophi.paywall.models.UserDimensions
import io.sophi.paywall.models.WallDecision
import io.sophi.paywall.repository.DeviceDimensionRepository
import io.sophi.paywall.repository.PaywallDeciderRepository
import io.sophi.paywall.repository.UserDimensionRepository
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.*


@RunWith(RobolectricTestRunner::class)
class PaywallDeciderTestAndroid {

    @BeforeTest
    fun setup() {
        Logger.setLogWriters(listOf(CommonWriter()))
        applicationContext = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testPaywallDecision() = runBlocking {
        val userDimensions: UserDimensionRepository = NativeUserDimensionRepository()
        val deviceDimensions: DeviceDimensionRepository = NativeDeviceDimensionRepository()
        val deciders = PaywallDeciderRepository.createNew(userDimensions, deviceDimensions)
        val thisDecider = deciders.getOneByHost(
            host = "www.statesman.com",
            settings = mapOf(
                "apiTimeoutInMilliSeconds" to "5000",
                "paywallApiEndpoint" to "http://paywall.sophi.codes"
            )
        )

        val decision: WallDecision = thisDecider.decide(
            contentId = "408e16fc-3714-49e8-b022-a35552509ff9",
            assignedGroup = "control",
            userProperties = mapOf(
                "countryCode" to "CA"
            )
        )

        println("Decision Outcome: $decision") //TODO: Remove debug print

        assertNotNull(decision.trace)
        assertEquals(decision.context, "AA03AB02AC01")
        assertContains(decision.inputs!!, "DB:America/New_York")
    }

    class NativeDeviceDimensionRepository : DeviceDimensionRepository {

        override fun getAll(): DeviceDimensions {
            return DeviceDimensions()
        }

    }

    class NativeUserDimensionRepository : UserDimensionRepository {
        override fun getAll(): UserDimensions {
            return UserDimensions(
                todayPageViews = 3,
                todayPageViewsByArticle = 2,
                todayPageViewsByArticleWithPaywall = 1,
                todayPageViewsByArticleWithRegwall = 0,
                todayTopLevelSections = 2,
                todayTopLevelSectionsByArticle = 1,
                sevenDayPageViews = 7,
                sevenDayPageViewsByArticle = 5,
                sevenDayPageViewsByArticleWithPaywall = 2,
                sevenDayPageViewsByArticleWithRegwall = 1,
                sevenDayTopLevelSections = 4,
                sevenDayTopLevelSectionsByArticle = 3,
                sevenDayVisitCount = 5,
                twentyEightDayPageViews = 15,
                twentyEightDayPageViewsByArticle = 10,
                twentyEightDayPageViewsByArticleWithPaywall = 5,
                twentyEightDayPageViewsByArticleWithRegwall = 2,
                twentyEightDayTopLevelSections = 8,
                twentyEightDayTopLevelSectionsByArticle = 6,
                twentyEightDayVisitCount = 12,
                daysSinceLastVisit = 2,
                visitorType = VisitorType.REGISTERED,
                visitor = UserDimensions.Visitor(
                    session = UserDimensions.Session(
                        campaignName = "SpringSale",
                        referrerDomain = "google.com",
                        referrerMedium = ReferrerMedium.SEARCH,
                        referrerSource = ReferrerSource.GOOGLE,
                        referrerChannel = ReferrerChannel.SEARCH,
                        timestamp = "2024-06-01T12:00:00Z"
                    )
                ),
                timeZone = "America/New_York",
                referrer = ReferrerMedium.SEARCH,
                referrerData = UserDimensions.ReferrerData(
                    medium = ReferrerMedium.SEARCH,
                    source = ReferrerSource.GOOGLE,
                    channel = ReferrerChannel.SEARCH
                )
            )
        }
    }
}
