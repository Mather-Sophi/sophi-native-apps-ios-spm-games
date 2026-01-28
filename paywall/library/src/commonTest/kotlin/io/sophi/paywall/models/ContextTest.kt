package io.sophi.paywall.models

import io.sophi.paywall.enums.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ContextTest {

    private fun createTestContext(
        todayPageViews: Int = 5,
        todayPageViewsByArticle: Int = 3,
        todayPageViewsByArticleWithPaywall: Int = 2,
        todayPageViewsByArticleWithRegwall: Int = 1,
        todayTopLevelSections: Int = 4,
        todayTopLevelSectionsByArticle: Int = 2,
        sevenDayPageViews: Int = 10,
        sevenDayPageViewsByArticle: Int = 8,
        sevenDayPageViewsByArticleWithPaywall: Int = 5,
        sevenDayPageViewsByArticleWithRegwall: Int = 3,
        sevenDayTopLevelSections: Int = 7,
        sevenDayTopLevelSectionsByArticle: Int = 6,
        sevenDayVisitCount: Int = 5,
        twentyEightDayPageViews: Int = 50,
        twentyEightDayPageViewsByArticle: Int = 40,
        twentyEightDayPageViewsByArticleWithPaywall: Int = 30,
        twentyEightDayPageViewsByArticleWithRegwall: Int = 20,
        twentyEightDayTopLevelSections: Int = 15,
        twentyEightDayTopLevelSectionsByArticle: Int = 12,
        twentyEightDayVisitCount: Int = 20,
        daysSinceLastVisit: Int = 5,
        timeZone: String = "America/Toronto",
        referrer: ReferrerMedium? = null,
        referrerData: UserDimensions.ReferrerData? = null,
        hourOfDay: Int = 14,
        os: String? = "ios",
        type: DeviceType? = DeviceType.NATIVE,
        viewer: String? = "app",
        visitorType: VisitorType = VisitorType.ANONYMOUS,
        visitor: UserDimensions.Visitor? = null,
        assignedGroup: String? = null,
        userProperties: Map<String, String?>? = null,
        content: ContentDimensions? = null
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
            content = content
        )
    }

    @Test
    fun testContextCreationWithValidValues() {
        val context = createTestContext()

        assertEquals(5, context.todayPageViews)
        assertEquals(10, context.sevenDayPageViews)
        assertEquals(50, context.twentyEightDayPageViews)
        assertEquals("America/Toronto", context.timeZone)
        assertEquals(14, context.hourOfDay)
    }

    @Test
    fun testGetEncodedContextWithAllValues() {
        val context = createTestContext()
        val encoded = context.getEncodedContext()

        assertContains(encoded, "DA05", message = "daysSinceLastVisit encoding does not match")

        assertContains(encoded, "AA05", message = "todayPageViews encoding does not match")
        assertContains(encoded, "AB03", message = "todayPageViewsByArticle encoding does not match")
        assertContains(encoded, "AC02", message = "todayPageViewsByArticleWithPaywall encoding does not match")
        assertContains(encoded, "AD04", message = "todayTopLevelSections encoding does not match")
        assertContains(encoded, "AE02", message = "todayTopLevelSectionsByArticle encoding does not match")
        assertContains(encoded, "AF01", message = "todayPageViewsByArticleWithRegwall encoding does not match")

        assertContains(encoded, "BA10", message = "sevenDayPageViews encoding does not match")
        assertContains(encoded, "BB08", message = "sevenDayPageViewsByArticle encoding does not match")
        assertContains(encoded, "BC05", message = "sevenDayPageViewsByArticleWithPaywall encoding does not match")
        assertContains(encoded, "BD07", message = "sevenDayTopLevelSections encoding does not match")
        assertContains(encoded, "BE06", message = "sevenDayTopLevelSectionsByArticle encoding does not match")
        assertContains(encoded, "BF05", message = "sevenDayVisitCount encoding does not match")
        assertContains(encoded, "BG03", message = "sevenDayPageViewsByArticleWithRegwall encoding does not match")

        assertContains(encoded, "CA50", message = "twentyEightDayPageViews encoding does not match")
        assertContains(encoded, "CB40", message = "twentyEightDayPageViewsByArticle encoding does not match")
        assertContains(encoded, "CC30", message = "twentyEightDayPageViewsByArticleWithPaywall encoding does not match")
        assertContains(encoded, "CD15", message = "twentyEightDayTopLevelSections encoding does not match")
        assertContains(encoded, "CE12", message = "twentyEightDayTopLevelSectionsByArticle encoding does not match")
        assertContains(encoded, "CF20", message = "twentyEightDayVisitCount encoding does not match")
        assertContains(encoded, "CG20", message = "twentyEightDayPageViewsByArticleWithRegwall encoding does not match")

    }

    @Test
    fun testGetEncodedInputsWithAllData() {
        val context = createTestContext(
            visitorType = VisitorType.ANONYMOUS,
            visitor = UserDimensions.Visitor(
                session = UserDimensions.Session(
                    campaignName = "sale",
                    referrerDomain = "google.com",
                    referrerMedium = ReferrerMedium.SEARCH,
                    referrerSource = ReferrerSource.DUCKDUCKGO,
                    referrerChannel = ReferrerChannel.SEARCH,
                    timestamp = "2024-12-01T12:00:00Z"
                )
            ),
            referrerData = UserDimensions.ReferrerData(
                medium = ReferrerMedium.DIRECT,
                source = ReferrerSource.REDDIT,
                channel = ReferrerChannel.NEWS
            ),
            assignedGroup = "variant"
        )

        val encoded = context.getEncodedInputs()


        // Experiment
        assertContains(encoded, "HB:02", message = "assignedGroup encoding does not match")

        // Visitor
        assertContains(encoded, "FA:00", message = "visitorType encoding does not match")

        // Referrer Data
        assertContains(encoded, "DE:01", message = "referrer medium encoding does not match")
        assertContains(encoded, "DF:08", message = "referrer source encoding does not match")
        assertContains(encoded, "DG:01", message = "referrer channel encoding does not match")

        // Device
        assertContains(encoded, "EA:ios", message = "os encoding does not match")
        assertContains(encoded, "EB:06", message = "type encoding does not match")
        assertContains(encoded, "EC:app", message = "viewer encoding does not match")

        // Timezone and Hour
        assertContains(encoded, "DD:14", message = "hourOfDay encoding does not match")
        assertContains(encoded, "DB:America/Toronto", message = "timeZone encoding does not match")

        // Visitor Session
        assertContains(encoded, "GA:sale", message = "session campaignName encoding does not match")
        assertContains(encoded, "GB:google.com", message = "session referrerDomain encoding does not match")
        assertContains(encoded, "GC:search", message = "session referrerMedium encoding does not match")
        assertContains(encoded, "GD:duckduckgo", message = "session referrerSource encoding does not match")
        assertContains(encoded, "GE:search", message = "session referrerChannel encoding does not match")
    }

    @Test
    fun testGetEncodedInputs_WithDeviceData() {
        val context = createTestContext(
            os = "android",
            viewer = "app-version-1"
        )

        val encoded = context.getEncodedInputs()

        assertContains(encoded, "EA:android", message = "Device os encoding does not match")
        assertContains(encoded, "EB:06", message = "Device type encoding does not match")
        assertContains(encoded, "EC:app-version-1", message = "Device viewer encoding does not match")
    }


    @Test
    fun testNewFromUserAndDeviceDimensions() {
        val userDimensions = UserDimensions(
            todayPageViews = 5,
            todayPageViewsByArticle = 3,
            todayPageViewsByArticleWithPaywall = 2,
            todayPageViewsByArticleWithRegwall = 1,
            todayTopLevelSections = 4,
            todayTopLevelSectionsByArticle = 2,
            sevenDayPageViews = 10,
            sevenDayPageViewsByArticle = 8,
            sevenDayPageViewsByArticleWithPaywall = 5,
            sevenDayPageViewsByArticleWithRegwall = 3,
            sevenDayTopLevelSections = 7,
            sevenDayTopLevelSectionsByArticle = 6,
            sevenDayVisitCount = 5,
            twentyEightDayPageViews = 50,
            twentyEightDayPageViewsByArticle = 40,
            twentyEightDayPageViewsByArticleWithPaywall = 30,
            twentyEightDayPageViewsByArticleWithRegwall = 20,
            twentyEightDayTopLevelSections = 15,
            twentyEightDayTopLevelSectionsByArticle = 12,
            twentyEightDayVisitCount = 20,
            daysSinceLastVisit = 5,
            timeZone = "America/Toronto",
            referrer = null,
            referrerData = null,
            visitorType = VisitorType.REGISTERED,
            visitor = null
        )

        val deviceDimensions = DeviceDimensions(
            hourOfDay = 15,
            os = "ios",
            type = DeviceType.MOBILE,
            viewer = "app"
        )

        val contentDimensions = ContentDimensions(
            id = "test-content-id",
            properties = mapOf("section" to "sports")
        )

        val context = Context.newFromUserAndDeviceDimensions(
            contentDimensions = contentDimensions,
            userDimensions = userDimensions,
            deviceDimensions = deviceDimensions,
            assignedGroup = "control"
        )

        assertEquals(5, context.todayPageViews)
        assertEquals(10, context.sevenDayPageViews)
        assertEquals("America/Toronto", context.timeZone)
        assertEquals(15, context.hourOfDay)
        assertEquals("ios", context.os)
        assertEquals(VisitorType.REGISTERED, context.visitorType)
        assertEquals("sports", context.content?.properties?.get("section"))
    }
}
