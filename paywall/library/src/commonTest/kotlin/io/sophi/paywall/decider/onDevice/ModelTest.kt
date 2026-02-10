package io.sophi.paywall.decider.onDevice

import io.sophi.paywall.configuration.BonusParameters
import io.sophi.paywall.enums.*
import io.sophi.paywall.models.ContentDimensions
import io.sophi.paywall.models.Context
import io.sophi.paywall.models.UserDimensions
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ModelTest {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private fun createTestContext(
        visitorType: VisitorType = VisitorType.ANONYMOUS,
        todayPageViews: Int = 5,
        todayPageViewsByArticle: Int = 3,
        todayPageViewsByArticleWithPaywall: Int = 1,
        todayPageViewsByArticleWithRegwall: Int = 2,
        todayTopLevelSections: Int = 2,
        todayTopLevelSectionsByArticle: Int = 1,
        sevenDayPageViews: Int = 10,
        sevenDayPageViewsByArticle: Int = 2,
        sevenDayPageViewsByArticleWithPaywall: Int = 5,
        sevenDayPageViewsByArticleWithRegwall: Int = 3,
        sevenDayTopLevelSections: Int = 5,
        sevenDayTopLevelSectionsByArticle: Int = 4,
        sevenDayVisitCount: Int = 3,
        twentyEightDayPageViews: Int = 20,
        twentyEightDayPageViewsByArticle: Int = 15,
        twentyEightDayPageViewsByArticleWithPaywall: Int = 10,
        twentyEightDayPageViewsByArticleWithRegwall: Int = 8,
        twentyEightDayTopLevelSections: Int = 12,
        twentyEightDayTopLevelSectionsByArticle: Int = 10,
        twentyEightDayVisitCount: Int = 8,
        daysSinceLastVisit: Int = 1,
        timeZone: String = "America/New_York",
        referrer: ReferrerMedium? = ReferrerMedium.SEARCH,
        referrerData: UserDimensions.ReferrerData? = UserDimensions.ReferrerData(
            medium = ReferrerMedium.SEARCH,
            source = ReferrerSource.GOOGLE,
            channel = ReferrerChannel.SEARCH
        ),
        hourOfDay: Int = 8,
        os: OSType? = OSType.IOS,
        type: DeviceType? = DeviceType.MOBILE,
        viewer: String? = "Safari",
        visitor: UserDimensions.Visitor? = UserDimensions.Visitor(
            session = UserDimensions.Session(
                timestamp = "2026-01-20T21:48:52.524Z",
                campaignName = "SpringSale",
                referrerDomain = "google.com",
                referrerMedium = ReferrerMedium.SEARCH,
                referrerSource = ReferrerSource.GOOGLE,
                referrerChannel = ReferrerChannel.SEARCH
            )
        ),
        assignedGroup: String? = "variant",
        userProperties: Map<String, Any?>? = mapOf(
            "countryCode" to "US",
            "subscriptionStatus" to "active"
        )
    ): Context {
        return Context(
            todayPageViews = todayPageViews,
            todayPageViewsByArticle = todayPageViewsByArticle,
            todayPageViewsByArticleWithPaywall = todayPageViewsByArticleWithPaywall,
            todayPageViewsByArticleWithRegwall = todayPageViewsByArticleWithRegwall,
            todayTopLevelSections = todayTopLevelSections,
            todayTopLevelSectionsByArticle = todayTopLevelSectionsByArticle,
            sevenDayPageViews = sevenDayPageViews,
            sevenDayPageViewsByArticle = sevenDayPageViewsByArticle,
            sevenDayPageViewsByArticleWithPaywall = sevenDayPageViewsByArticleWithPaywall,
            sevenDayPageViewsByArticleWithRegwall = sevenDayPageViewsByArticleWithRegwall,
            sevenDayTopLevelSections = sevenDayTopLevelSections,
            sevenDayTopLevelSectionsByArticle = sevenDayTopLevelSectionsByArticle,
            sevenDayVisitCount = sevenDayVisitCount,
            twentyEightDayPageViews = twentyEightDayPageViews,
            twentyEightDayPageViewsByArticle = twentyEightDayPageViewsByArticle,
            twentyEightDayPageViewsByArticleWithPaywall = twentyEightDayPageViewsByArticleWithPaywall,
            twentyEightDayPageViewsByArticleWithRegwall = twentyEightDayPageViewsByArticleWithRegwall,
            twentyEightDayTopLevelSections = twentyEightDayTopLevelSections,
            twentyEightDayTopLevelSectionsByArticle = twentyEightDayTopLevelSectionsByArticle,
            twentyEightDayVisitCount = twentyEightDayVisitCount,
            daysSinceLastVisit = daysSinceLastVisit,
            timeZone = timeZone,
            referrer = referrer,
            referrerData = referrerData,
            hourOfDay = hourOfDay,
            os = os,
            type = type,
            viewer = viewer,
            visitorType = visitorType,
            visitor = visitor,
            assignedGroup = assignedGroup,
            userProperties = userProperties,
            content = ContentDimensions(id = "test-content-id")
        )
    }

    private val context = createTestContext()

    @Test
    fun `applyBonus handles todayStopRate parameter`() {
        // Validates: applyBonus() handles todayStopRate parameter
        // When context stopRate doesn't match configured values, no bonus is applied
        val bonusParametersJson = """{"todayStopRate": {"0.0": 0.68, "0.1": 4.43}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(0.0, actualBonus)
    }

    @Test
    fun `applyBonus handles sevenDayStopRate parameter correctly`() {
        // Validates: applyBonus() handles sevenDayStopRate parameter correctly
        val bonusParametersJson = """{"sevenDaysStopRate": {"0.0": 0.33, "0.1": 0.69}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(0.0, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for todayPageViews correctly`() {
        val bonusParametersJson = """{"todayPageViews": {"5": 0.25, "10": 0.5}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.25
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for todayPageViewsByArticle correctly`() {
        val bonusParametersJson = """{"todayPageViewsByArticle": {"3": 0.71, "5": 0.83}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.71
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for todayPageViewsByArticleWithPaywall correctly`() {
        val bonusParametersJson = """{"todayPageViewsByArticleWithPaywall": {"1": 0.15, "2": 0.25}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.15
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for todayPageViewsByArticleWithRegwall correctly`() {
        val bonusParametersJson = """{"todayPageViewsByArticleWithRegwall": {"2": 0.35, "3": 0.45}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.35
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for todayTopLevelSections correctly`() {
        val bonusParametersJson = """{"todayTopLevelSections": {"2": 0.61, "3": 0.73}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.61
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for todayTopLevelSectionsByArticle correctly`() {
        val bonusParametersJson = """{"todayTopLevelSectionsByArticle": {"1": -0.04, "2": -0.02}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * (-0.04)
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sevenDayPageViews correctly`() {
        val bonusParametersJson = """{"sevenDayPageViews": {"10": 0.8, "15": 0.9}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.8
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sevenDayPageViewsByArticle correctly`() {
        val bonusParametersJson = """{"sevenDayPageViewsByArticle": {"2": -0.41, "3": -0.37}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * (-0.41)
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sevenDayPageViewsByArticleWithPaywall correctly`() {
        val bonusParametersJson = """{"sevenDayPageViewsByArticleWithPaywall": {"5": 0.22, "6": 0.28}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.22
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sevenDayPageViewsByArticleWithRegwall correctly`() {
        val bonusParametersJson = """{"sevenDayPageViewsByArticleWithRegwall": {"3": 0.18, "4": 0.24}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.18
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sevenDayTopLevelSections correctly`() {
        val bonusParametersJson = """{"sevenDayTopLevelSections": {"5": 0.45, "6": 0.50}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.45
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sevenDayTopLevelSectionsByArticle correctly`() {
        val bonusParametersJson = """{"sevenDayTopLevelSectionsByArticle": {"4": 0.33, "5": 0.38}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.33
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sevenDayVisitCount correctly`() {
        val bonusParametersJson = """{"sevenDayVisitCount": {"3": 0.28, "4": 0.32}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.28
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for twentyEightDayPageViews correctly`() {
        val bonusParametersJson = """{"twentyEightDayPageViews": {"20": 0.95, "25": 1.0}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.95
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for twentyEightDayPageViewsByArticle correctly`() {
        val bonusParametersJson = """{"twentyEightDayPageViewsByArticle": {"15": 0.88, "20": 0.92}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.88
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for twentyEightDayPageViewsByArticleWithPaywall correctly`() {
        val bonusParametersJson = """{"twentyEightDayPageViewsByArticleWithPaywall": {"10": 0.55, "12": 0.60}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.55
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for twentyEightDayPageViewsByArticleWithRegwall correctly`() {
        val bonusParametersJson = """{"twentyEightDayPageViewsByArticleWithRegwall": {"8": 0.42, "10": 0.48}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.42
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for twentyEightDayTopLevelSections correctly`() {
        val bonusParametersJson = """{"twentyEightDayTopLevelSections": {"12": 0.75, "15": 0.80}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.75
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for twentyEightDayTopLevelSectionsByArticle correctly`() {
        val bonusParametersJson = """{"twentyEightDayTopLevelSectionsByArticle": {"10": 0.68, "12": 0.72}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.68
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for twentyEightDayVisitCount correctly`() {
        val bonusParametersJson = """{"twentyEightDayVisitCount": {"8": 0.62, "10": 0.68}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.62
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for daysSinceLastVisit correctly`() {
        val bonusParametersJson = """{"daysSinceLastVisit": {"1": 0.28, "2": -0.34}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.28
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for timeZone correctly`() {
        val bonusParametersJson = """{"timeZone": {"america/new_york": 0.1, "europe/london": 0.05}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.1
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for referrer correctly`() {
        val bonusParametersJson = """{"referrer": {"search": -3.0, "direct": -0.02}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * (-3.0)
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for hourOfDay correctly`() {
        val bonusParametersJson = """{"hourOfDay": {"14": 0.13, "8": 0.16}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.16
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for os correctly`() {
        val bonusParametersJson = """{"os": {"ios": 0.3, "android": -0.5}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.3
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for type correctly`() {
        val bonusParametersJson = """{"type": {"mobile": -0.4, "tablet": 0.2}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * (-0.4)
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for viewer correctly`() {
        val bonusParametersJson = """{"viewer": {"safari": 1.07, "chrome": -0.36}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 1.07
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for visitorType correctly`() {
        val bonusParametersJson = """{"visitorType": {"anonymous": 0.5, "registered": 0.2}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.5
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for assignedGroup correctly`() {
        val bonusParametersJson = """{"assignedGroup": {"variant": 0.2, "control": 0.0}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.2
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sessionCampaignName correctly`() {
        val bonusParametersJson = """{"sessionCampaignName": {"springsale": 0.3, "summersale": 0.25}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.3
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sessionReferrerDomain correctly`() {
        val bonusParametersJson = """{"sessionReferrerDomain": {"google.com": 0.35, "example.com": 0.5}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.35
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sessionReferrerMedium correctly`() {
        val bonusParametersJson = """{"sessionReferrerMedium": {"search": 0.4, "social": 0.15}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.4
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sessionReferrerSource correctly`() {
        val bonusParametersJson = """{"sessionReferrerSource": {"google": 0.3, "facebook": 0.2}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.3
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for sessionReferrerChannel correctly`() {
        val bonusParametersJson = """{"sessionReferrerChannel": {"search": 0.25, "news": 0.15}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.25
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for referrerDataMedium correctly`() {
        val bonusParametersJson = """{"referrerDataMedium": {"search": 0.1, "campaign": 0.5}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.1
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for referrerDataSource correctly`() {
        val bonusParametersJson = """{"referrerDataSource": {"google": 0.3, "yahoo": 0.18}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.3
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus calculates bonus for referrerDataChannel correctly`() {
        val bonusParametersJson = """{"referrerDataChannel": {"search": 0.4, "discover": 0.22}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.4
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus accumulates bonus for multiple user properties`() {
        val bonusParametersJson = """
        {
            "userProperties": {
                "countryCode": {"US": 0.45, "CA": 0.32},
                "subscriptionStatus": {"active": 0.65, "expired": -0.28}
            }
        }
        """
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = averageBonus * 0.45 + averageBonus * 0.65
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus accumulates bonus for multiple parameter types`() {
        val bonusParametersJson = """
        {
            "daysSinceLastVisit": {"1": 0.28},
            "hourOfDay": {"8": 0.13},
            "referrer": {"search": -3.0},
            "os": {"ios": 0.3},
            "visitorType": {"anonymous": 0.5}
        }
        """
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        var expectedBonus = 0.0
        expectedBonus += averageBonus * 0.28
        expectedBonus += averageBonus * 0.13
        expectedBonus += averageBonus * (-3.0)
        expectedBonus += averageBonus * 0.3
        expectedBonus += averageBonus * 0.5

        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus adds to initial bonus value`() {
        val bonusParametersJson = """{"daysSinceLastVisit": {"1": 0.28}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1
        val initialBonus = 0.5

        val expectedBonus = initialBonus + (averageBonus * 0.28)
        val actualBonus = applyBonus(initialBonus, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    @Test
    fun `applyBonus returns zero when no values match`() {
        val bonusParametersJson = """{"daysSinceLastVisit": {"0": 0.01, "5": 0.35}}"""
        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)
        val averageBonus = 0.1

        val expectedBonus = 0.0
        val actualBonus = applyBonus(0.0, averageBonus, context, bonusParameters)
        assertEquals(expectedBonus, actualBonus)
    }

    // ========== Tests for predict() function ==========

    @Test
    fun `predict returns both REGWALL and PAYWALL outcomes for anonymous visitors`() {
        val parametersJson = """{
            "avgScore": 0.7,
            "avgBonus": 0.18,
            "avgRegwallBonus": 0.15,
            "isRandomized": false,
            "scoreRandomizationFactor": 0.03,
            "visitorPaywallBonusParameters": {
                "anonymous": {},
                "registered": {}
            },
            "regwallBonusParameters": {}
        }"""
        val parameters = json.decodeFromString<io.sophi.paywall.configuration.Parameters>(parametersJson)
        val model = Model(parameters)

        val testContext = createTestContext(visitorType = VisitorType.ANONYMOUS)
        val outcomes = model.predict(testContext)

        assertEquals(2, outcomes.size, "Anonymous visitor should return 2 outcomes")
        assertEquals(WallType.REGWALL, outcomes[0].wallType)
        assertEquals(WallType.PAYWALL, outcomes[1].wallType)
    }

    @Test
    fun `predict returns only PAYWALL outcome for registered visitors`() {
        val parametersJson = """{
            "avgScore": 0.7,
            "avgBonus": 0.18,
            "avgRegwallBonus": 0.15,
            "isRandomized": false,
            "scoreRandomizationFactor": 0.03,
            "visitorPaywallBonusParameters": {
                "anonymous": {},
                "registered": {}
            },
            "regwallBonusParameters": {}
        }"""
        val parameters = json.decodeFromString<io.sophi.paywall.configuration.Parameters>(parametersJson)
        val model = Model(parameters)

        val testContext = createTestContext(visitorType = VisitorType.REGISTERED)
        val outcomes = model.predict(testContext)

        assertEquals(1, outcomes.size, "Registered visitor should return 1 outcome")
        assertEquals(WallType.PAYWALL, outcomes[0].wallType)
    }

    @Test
    fun `predict calculates score correctly applying bonus multipliers`() {
        val parametersJson = """{
            "avgScore": 0.7,
            "avgBonus": 0.18,
            "avgRegwallBonus": 0.15,
            "isRandomized": false,
            "scoreRandomizationFactor": 0.03,
            "visitorPaywallBonusParameters": {
                "anonymous": {
                    "daysSinceLastVisit": {"1": 0.32}
                },
                "registered": {}
            },
            "regwallBonusParameters": {}
        }"""
        val parameters = json.decodeFromString<io.sophi.paywall.configuration.Parameters>(parametersJson)
        val model = Model(parameters)

        val testContext = createTestContext(
            visitorType = VisitorType.ANONYMOUS,
            daysSinceLastVisit = 1
        )
        val outcomes = model.predict(testContext)

        assertEquals(0.7 + (0.32 * 0.18), outcomes[1].score!!, 0.0001)
    }

    @Test
    fun `predict applies score randomization within expected range when enabled`() {
        val parametersJson = """{
            "avgScore": 0.7,
            "avgBonus": 0.18,
            "avgRegwallBonus": 0.15,
            "isRandomized": true,
            "scoreRandomizationFactor": 0.03,
            "visitorPaywallBonusParameters": {
                "anonymous": {},
                "registered": {}
            },
            "regwallBonusParameters": {}
        }"""
        val parameters = json.decodeFromString<io.sophi.paywall.configuration.Parameters>(parametersJson)
        val model = Model(parameters)

        val testContext = createTestContext(visitorType = VisitorType.ANONYMOUS)
        val outcomes = model.predict(testContext)

        val score = outcomes[1].score!!
        assertTrue(
            score >= 0.685 && score <= 0.715,
            "Score with randomization should be between 0.685 and 0.715, got $score"
        )
    }

    @Test
    fun `predict buckets low scores to zero point zero one`() {
        val parametersJson = """{
            "avgScore": 0.05,
            "avgBonus": 0.0,
            "avgRegwallBonus": 0.0,
            "isRandomized": false,
            "scoreRandomizationFactor": 0.03,
            "visitorPaywallBonusParameters": {
                "anonymous": {},
                "registered": {}
            },
            "regwallBonusParameters": {}
        }"""
        val parameters = json.decodeFromString<io.sophi.paywall.configuration.Parameters>(parametersJson)
        val model = Model(parameters)

        val testContext = createTestContext(visitorType = VisitorType.REGISTERED)
        val outcomes = model.predict(testContext)

        // Low scores (<= 0.1) should be bucketed to 0.01
        assertEquals(0.01, outcomes[0].score!!, 0.0001)
    }

    @Test
    fun `predict accumulates multiple bonus parameters independently for paywall and regwall`() {
        val parametersJson = """{
            "avgScore": 0.7,
            "avgBonus": 0.18,
            "avgRegwallBonus": 0.15,
            "isRandomized": false,
            "scoreRandomizationFactor": 0.03,
            "visitorPaywallBonusParameters": {
                "anonymous": {
                    "daysSinceLastVisit": {"0": 0.32},
                    "hourOfDay": {"8": -0.04},
                    "referrer": {"search": 1.1}
                },
                "registered": {}
            },
            "regwallBonusParameters": {
                "daysSinceLastVisit": {"0": 0.5}
            }
        }"""
        val parameters = json.decodeFromString<io.sophi.paywall.configuration.Parameters>(parametersJson)
        val model = Model(parameters)

        val testContext = createTestContext(
            visitorType = VisitorType.ANONYMOUS,
            daysSinceLastVisit = 0,
            hourOfDay = 8,
            referrer = ReferrerMedium.SEARCH
        )
        val outcomes = model.predict(testContext)

        // Verify both paywall and regwall scores are calculated
        assertNotNull(outcomes[0].score, "Regwall score should not be null")
        assertNotNull(outcomes[1].score, "Paywall score should not be null")

        // Paywall bonus: (0.32 + (-0.04) + 1.1) * 0.18 = 0.2484
        assertEquals(0.7 + (1.38 * 0.18), outcomes[1].score!!, 0.0001)

        // Regwall bonus: 0.5 * 0.15 = 0.075
        assertEquals(0.7 + 0.075, outcomes[0].score!!, 0.0001)
    }
}
