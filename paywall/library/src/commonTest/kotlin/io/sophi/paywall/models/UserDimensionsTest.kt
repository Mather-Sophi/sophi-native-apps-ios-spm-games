package io.sophi.paywall.models

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import io.sophi.paywall.enums.ReferrerChannel
import io.sophi.paywall.enums.ReferrerMedium
import io.sophi.paywall.enums.ReferrerSource
import io.sophi.paywall.enums.VisitorType
import kotlin.test.*

class UserDimensionsTest {

    @BeforeTest
    fun setup() {
        Logger.setLogWriters(listOf(CommonWriter()))
        Logger.setMinSeverity(Severity.Debug)
    }

    @Test
    fun testValidUserDimensions() {
        val userDimensions = UserDimensions(
            todayPageViews = 5,
            sevenDayPageViews = 10,
            twentyEightDayPageViews = 15,
            visitorType = VisitorType.REGISTERED,
            visitor = UserDimensions.Visitor(
                session = UserDimensions.Session(
                    referrerMedium = ReferrerMedium.SEARCH,
                    referrerSource = ReferrerSource.GOOGLE,
                    timestamp = "2024-06-01T12:00:00Z"
                )
            ),
            referrerData = UserDimensions.ReferrerData(
                medium = ReferrerMedium.SEARCH,
                source = ReferrerSource.GOOGLE
            )
        )

        assertEquals(VisitorType.REGISTERED, userDimensions.visitorType)
        assertEquals(5, userDimensions.todayPageViews)
    }


    @Test
    fun testFromMapWithAllFields() {
        val data = mapOf(
            "todayPageViews" to 1,
            "todayPageViewsByArticle" to 2,
            "todayPageViewsByArticleWithPaywall" to 3,
            "todayPageViewsByArticleWithRegwall" to 4,
            "todayTopLevelSections" to 5,
            "todayTopLevelSectionsByArticle" to 6,
            "sevenDayPageViews" to 7,
            "sevenDayPageViewsByArticle" to 8,
            "sevenDayPageViewsByArticleWithPaywall" to 9,
            "sevenDayPageViewsByArticleWithRegwall" to 10,
            "sevenDayTopLevelSections" to 11,
            "sevenDayTopLevelSectionsByArticle" to 12,
            "sevenDayVisitCount" to 13,
            "twentyEightDayPageViews" to 14,
            "twentyEightDayPageViewsByArticle" to 15,
            "twentyEightDayPageViewsByArticleWithPaywall" to 16,
            "twentyEightDayPageViewsByArticleWithRegwall" to 17,
            "twentyEightDayTopLevelSections" to 18,
            "twentyEightDayTopLevelSectionsByArticle" to 19,
            "twentyEightDayVisitCount" to 20,
            "daysSinceLastVisit" to 21,
            "visitorType" to "anonymous",
            "visitor" to mapOf(
                "session" to mapOf(
                    "campaignName" to "SpringSale",
                    "referrerDomain" to "facebook.com",
                    "referrerMedium" to "social",
                    "referrerSource" to "facebook",
                    "referrerChannel" to "news",
                    "timestamp" to "2024-06-01T12:00:00Z"
                )
            ),
            "timeZone" to "America/New_York",
            "referrer" to "internal",
            "referrerData" to mapOf(
                "medium" to "direct",
                "source" to "newsletter",
                "channel" to "discover"
            )
        )

        val userDimensions = UserDimensions.fromMap(data)
        assertEquals(1, userDimensions.todayPageViews)
        assertEquals(2, userDimensions.todayPageViewsByArticle)
        assertEquals(3, userDimensions.todayPageViewsByArticleWithPaywall)
        assertEquals(4, userDimensions.todayPageViewsByArticleWithRegwall)
        assertEquals(5, userDimensions.todayTopLevelSections)
        assertEquals(6, userDimensions.todayTopLevelSectionsByArticle)
        assertEquals(7, userDimensions.sevenDayPageViews)
        assertEquals(8, userDimensions.sevenDayPageViewsByArticle)
        assertEquals(9, userDimensions.sevenDayPageViewsByArticleWithPaywall)
        assertEquals(10, userDimensions.sevenDayPageViewsByArticleWithRegwall)
        assertEquals(11, userDimensions.sevenDayTopLevelSections)
        assertEquals(12, userDimensions.sevenDayTopLevelSectionsByArticle)
        assertEquals(13, userDimensions.sevenDayVisitCount)
        assertEquals(14, userDimensions.twentyEightDayPageViews)
        assertEquals(15, userDimensions.twentyEightDayPageViewsByArticle)
        assertEquals(16, userDimensions.twentyEightDayPageViewsByArticleWithPaywall)
        assertEquals(17, userDimensions.twentyEightDayPageViewsByArticleWithRegwall)
        assertEquals(18, userDimensions.twentyEightDayTopLevelSections)
        assertEquals(19, userDimensions.twentyEightDayTopLevelSectionsByArticle)
        assertEquals(20, userDimensions.twentyEightDayVisitCount)
        assertEquals(21, userDimensions.daysSinceLastVisit)
        assertEquals(VisitorType.ANONYMOUS, userDimensions.visitorType)
        assertEquals("SpringSale", userDimensions.visitor?.session?.campaignName)
        assertEquals("facebook.com", userDimensions.visitor?.session?.referrerDomain)
        assertEquals(ReferrerMedium.SOCIAL, userDimensions.visitor?.session?.referrerMedium)
        assertEquals(ReferrerSource.FACEBOOK, userDimensions.visitor?.session?.referrerSource)
        assertEquals(ReferrerChannel.NEWS, userDimensions.visitor?.session?.referrerChannel)
        assertEquals("2024-06-01T12:00:00Z", userDimensions.visitor?.session?.timestamp)
        assertEquals("America/New_York", userDimensions.timeZone)
        assertEquals(ReferrerMedium.INTERNAL, userDimensions.referrer)
        assertEquals(ReferrerMedium.DIRECT, userDimensions.referrerData?.medium)
        assertEquals(ReferrerSource.NEWSLETTER, userDimensions.referrerData?.source)
        assertEquals(ReferrerChannel.DISCOVER, userDimensions.referrerData?.channel)
    }

    @Test
    fun testFromMapWithIntFieldsAsDouble() {
        val data = mapOf(
            "twentyEightDayTopLevelSections" to 15.0,
            "daysSinceLastVisit" to 0.0,
            "visitorType" to "anonymous",
            "sevenDayPageViewsByArticle" to 15.0,
            "referrerData" to mapOf(
                "channel" to "search",
                "medium" to "search",
                "source" to "google"
            ),
            "twentyEightDayVisitCount" to 18.0,
            "sevenDayPageViewsByArticleWithPaywall" to 5.0,
            "countryCode" to null,
            "twentyEightDayPageViews" to 100.0,
            "todayTopLevelSectionsByArticle" to 1.0,
            "sevenDayPageViewsByArticleWithRegwall" to 2.0,
            "todayTopLevelSections" to 2.0,
            "sevenDayPageViews" to 25.0,
            "timeZone" to "America/New_York",
            "todayPageViewsByArticle" to 3.0,
            "twentyEightDayPageViewsByArticleWithRegwall" to 10.0,
            "sevenDayTopLevelSections" to 8.0,
            "todayPageViewsByArticleWithPaywall" to 1.0,
            "referrer" to "search",
            "todayPageViews" to 5.0,
            "sevenDayVisitCount" to 5.0,
            "twentyEightDayPageViewsByArticle" to 60.0,
            "twentyEightDayTopLevelSectionsByArticle" to 12.0,
            "visitor" to mapOf(
                "session" to mapOf(
                    "referrerDomain" to "google.com",
                    "referrerChannel" to "search",
                    "referrerMedium" to "search",
                    "referrerSource" to "google",
                    "campaignName" to "SpringSale",
                    "timestamp" to "2026-01-20T21:48:52.524Z"
                )
            ),
            "twentyEightDayPageViewsByArticleWithPaywall" to 20.0,
            "sevenDayTopLevelSectionsByArticle" to 6.0,
            "todayPageViewsByArticleWithRegwall" to 0.0
        )

        val userDimensions = UserDimensions.fromMap(data)
        assertEquals(15, userDimensions.twentyEightDayTopLevelSections)
        assertEquals(0, userDimensions.daysSinceLastVisit)
        assertEquals(VisitorType.ANONYMOUS, userDimensions.visitorType)
        assertEquals(15, userDimensions.sevenDayPageViewsByArticle)
        assertEquals(ReferrerMedium.SEARCH, userDimensions.referrerData?.medium)
        assertEquals(18, userDimensions.twentyEightDayVisitCount)
        assertEquals(5, userDimensions.sevenDayPageViewsByArticleWithPaywall)
        assertEquals(100, userDimensions.twentyEightDayPageViews)
        assertEquals(1, userDimensions.todayTopLevelSectionsByArticle)
        assertEquals(2, userDimensions.sevenDayPageViewsByArticleWithRegwall)
        assertEquals(2, userDimensions.todayTopLevelSections)
        assertEquals(25, userDimensions.sevenDayPageViews)
        assertEquals(3, userDimensions.todayPageViewsByArticle)
        assertEquals(10, userDimensions.twentyEightDayPageViewsByArticleWithRegwall)
        assertEquals(8, userDimensions.sevenDayTopLevelSections)
        assertEquals(1, userDimensions.todayPageViewsByArticleWithPaywall)
        assertEquals("search", userDimensions.referrer?.value)
        assertEquals(5, userDimensions.todayPageViews)
        assertEquals(5, userDimensions.sevenDayVisitCount)
        assertEquals(60, userDimensions.twentyEightDayPageViewsByArticle)
        assertEquals(12, userDimensions.twentyEightDayTopLevelSectionsByArticle)
        assertEquals("google.com", userDimensions.visitor?.session?.referrerDomain)
        assertEquals(ReferrerChannel.SEARCH, userDimensions.visitor?.session?.referrerChannel)
        assertEquals(ReferrerMedium.SEARCH, userDimensions.visitor?.session?.referrerMedium)
        assertEquals(ReferrerSource.GOOGLE, userDimensions.visitor?.session?.referrerSource)
        assertEquals("SpringSale", userDimensions.visitor?.session?.campaignName)
        assertEquals("2026-01-20T21:48:52.524Z", userDimensions.visitor?.session?.timestamp)
        assertEquals(20, userDimensions.twentyEightDayPageViewsByArticleWithPaywall)
        assertEquals(6, userDimensions.sevenDayTopLevelSectionsByArticle)
        assertEquals(0, userDimensions.todayPageViewsByArticleWithRegwall)
    }

    @Test
    fun testFromMapWithEmptyVisitor() {
        val data = mapOf(
            "todayPageViews" to 3,
            "visitor" to null
        )

        val userDimensions = UserDimensions.fromMap(data)
        assertNull(userDimensions.visitor)
    }
}

class VisitorSessionValidationTest {

    @Test
    fun testValidSession() {
        val session = UserDimensions.Session(
            referrerMedium = ReferrerMedium.SEARCH,
            referrerSource = ReferrerSource.GOOGLE,
            referrerChannel = ReferrerChannel.SEARCH,
            timestamp = "2024-06-01T12:00:00Z"
        )

        assertEquals(ReferrerMedium.SEARCH, session.referrerMedium)
        assertEquals(ReferrerSource.GOOGLE, session.referrerSource)
        assertEquals(ReferrerChannel.SEARCH, session.referrerChannel)
    }

    @Test
    fun testInvalidTimestamp() {
        val ex = assertFailsWith<IllegalArgumentException> {
            UserDimensions.Session(timestamp = "2024-06-01 12:00:00")
        }
        assertEquals(
            "timestamp value '2024-06-01 12:00:00' must be in valid ISO 8601 format. e.g. YYYY-MM-DDTHH:MM:SSZ or with timezone offset.",
            ex.message
        )
    }

    @Test
    fun testValidTimestamps() {
        UserDimensions.Session(timestamp = "2024-06-01T12:00:00Z")
        UserDimensions.Session(timestamp = "2024-06-01T12:00:00+00:00")
        UserDimensions.Session(timestamp = "2024-06-01T08:00:00.02345-04:00")
    }

    @Test
    fun testFromMapWithValidData() {
        val data = mapOf(
            "referrerMedium" to "search",
            "referrerSource" to "google",
            "timestamp" to "2024-06-01T12:00:00Z"
        )

        val session = UserDimensions.Session.fromMap(data)
        assertEquals(ReferrerMedium.SEARCH, session?.referrerMedium)
        assertEquals(ReferrerSource.GOOGLE, session?.referrerSource)
    }

    @Test
    fun testFromMapWithEmptyData() {
        val session = UserDimensions.Session.fromMap(null)
        assertNull(session)
    }
}

class ReferrerDataValidationTest {

    @Test
    fun testValidReferrerData() {
        val referrerData = UserDimensions.ReferrerData(
            medium = ReferrerMedium.SEARCH,
            source = ReferrerSource.GOOGLE,
            channel = ReferrerChannel.NEWS
        )

        assertEquals("search", referrerData.medium.value)
        assertEquals("google", referrerData.source!!.value)
        assertEquals("news", referrerData.channel!!.value)
    }

    @Test
    fun testFromMapWithAllFields() {
        val data = mapOf(
            "medium" to "social",
            "source" to "facebook",
            "channel" to "news"
        )

        val referrerData = UserDimensions.ReferrerData.fromMap(data)
        assertEquals("social", referrerData?.medium?.value)
        assertEquals("facebook", referrerData?.source?.value)
        assertEquals("news", referrerData?.channel?.value)
    }

    @Test
    fun testValidUTMParametersFromMap() {
        val data = mapOf(
            "medium" to "social",
            "source" to "facebook",
            "channel" to "news",
            "utmParams" to mapOf(
                "utm_source" to "facebook",
                "utm_medium" to "social",
                "utm_campaign" to "summer-promo"
            )
        )

        val referrerData = UserDimensions.ReferrerData.fromMap(data)
        assertEquals(ReferrerMedium.SOCIAL, referrerData?.medium)
        assertEquals(ReferrerSource.FACEBOOK, referrerData?.source)
        assertEquals(ReferrerChannel.NEWS, referrerData?.channel)
        assertEquals("facebook", referrerData?.utmParams?.get("utm_source"))
        assertEquals("social", referrerData?.utmParams?.get("utm_medium"))
        assertEquals("summer-promo", referrerData?.utmParams?.get("utm_campaign"))
    }

    @Test
    fun testFromMapMissingMedium() {
        val ex = assertFailsWith<IllegalArgumentException> {
            UserDimensions.ReferrerData.fromMap(mapOf("source" to "GOOGLE"))
        }
        assertEquals("ReferrerData medium is required", ex.message)
    }
}
