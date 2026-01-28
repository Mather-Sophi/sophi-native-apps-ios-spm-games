package io.sophi.paywall.models

import io.sophi.paywall.configuration.PropertyCodes
import io.sophi.paywall.enums.DeviceType
import io.sophi.paywall.enums.ExperimentGroup
import io.sophi.paywall.enums.ReferrerMedium
import io.sophi.paywall.enums.VisitorType

class Context(
    // 1 day
    var todayPageViews: Int,
    var todayPageViewsByArticle: Int,
    var todayPageViewsByArticleWithPaywall: Int,
    var todayPageViewsByArticleWithRegwall: Int,
    var todayTopLevelSections: Int,
    var todayTopLevelSectionsByArticle: Int,

    // 7 days
    var sevenDayPageViews: Int,
    var sevenDayPageViewsByArticle: Int,
    var sevenDayPageViewsByArticleWithPaywall: Int,
    var sevenDayPageViewsByArticleWithRegwall: Int,
    var sevenDayTopLevelSections: Int,
    var sevenDayTopLevelSectionsByArticle: Int,
    var sevenDayVisitCount: Int,

    // 28 days
    var twentyEightDayPageViews: Int,
    var twentyEightDayPageViewsByArticle: Int,
    var twentyEightDayPageViewsByArticleWithPaywall: Int,
    var twentyEightDayPageViewsByArticleWithRegwall: Int,
    var twentyEightDayTopLevelSections: Int,
    var twentyEightDayTopLevelSectionsByArticle: Int,
    var twentyEightDayVisitCount: Int,

    // visit
    var daysSinceLastVisit: Int,
    var timeZone: String, // e.g. "America/New_York"
    var referrer: ReferrerMedium?,
    var referrerData: UserDimensions.ReferrerData?,
    var hourOfDay: Int,

    // device
    var os: String?,
    var type: DeviceType?,
    var viewer: String?,

    // user
    var visitorType: VisitorType,
    var visitor: UserDimensions.Visitor?,
    var assignedGroup: String? = null,
    var userProperties: Map<String, Any?>?,
    val content: ContentDimensions?,
) {

    companion object Factory {
        fun newFromUserAndDeviceDimensions(
            contentDimensions: ContentDimensions,
            userDimensions: UserDimensions,
            deviceDimensions: DeviceDimensions,
            userProperties: Map<String, Any?>? = emptyMap(),
            assignedGroup: String?
        ): Context {

            return Context(
                todayPageViews = userDimensions.todayPageViews,
                todayPageViewsByArticle = userDimensions.todayPageViewsByArticle,
                todayPageViewsByArticleWithPaywall = userDimensions.todayPageViewsByArticleWithPaywall,
                todayPageViewsByArticleWithRegwall = userDimensions.todayPageViewsByArticleWithRegwall,
                todayTopLevelSections = userDimensions.todayTopLevelSections,
                todayTopLevelSectionsByArticle = userDimensions.todayTopLevelSectionsByArticle,
                sevenDayPageViews = userDimensions.sevenDayPageViews,
                sevenDayPageViewsByArticle = userDimensions.sevenDayPageViewsByArticle,
                sevenDayPageViewsByArticleWithPaywall = userDimensions.sevenDayPageViewsByArticleWithPaywall,
                sevenDayPageViewsByArticleWithRegwall = userDimensions.sevenDayPageViewsByArticleWithRegwall,
                sevenDayTopLevelSections = userDimensions.sevenDayTopLevelSections,
                sevenDayTopLevelSectionsByArticle = userDimensions.sevenDayTopLevelSectionsByArticle,
                sevenDayVisitCount = userDimensions.sevenDayVisitCount,
                twentyEightDayPageViews = userDimensions.twentyEightDayPageViews,
                twentyEightDayPageViewsByArticle = userDimensions.twentyEightDayPageViewsByArticle,
                twentyEightDayPageViewsByArticleWithPaywall = userDimensions.twentyEightDayPageViewsByArticleWithPaywall,
                twentyEightDayPageViewsByArticleWithRegwall = userDimensions.twentyEightDayPageViewsByArticleWithRegwall,
                twentyEightDayTopLevelSections = userDimensions.twentyEightDayTopLevelSections,
                twentyEightDayTopLevelSectionsByArticle = userDimensions.twentyEightDayTopLevelSectionsByArticle,
                twentyEightDayVisitCount = userDimensions.twentyEightDayVisitCount,
                daysSinceLastVisit = userDimensions.daysSinceLastVisit,
                timeZone = userDimensions.timeZone ?: "unknown",
                referrer = userDimensions.referrer,
                referrerData = userDimensions.referrerData,
                hourOfDay = deviceDimensions.hourOfDay,
                os = deviceDimensions.os,
                type = deviceDimensions.type,
                viewer = deviceDimensions.viewer,
                visitorType = userDimensions.visitorType,
                visitor = userDimensions.visitor,
                assignedGroup = assignedGroup,
                userProperties = userProperties,
                content = contentDimensions
            )
        }
    }

    fun getEncodedContext(): String {
        fun getCode(featureCode: String, value: Int?): String {
            if (value == null) {
                return ""
            }
            return featureCode + value.toString().padStart(2, '0')
        }

        val context = StringBuilder()

        context.append(getCode("DA", daysSinceLastVisit))
        context.append(getCode("AA", todayPageViews))
        context.append(getCode("AB", todayPageViewsByArticle))
        context.append(getCode("AC", todayPageViewsByArticleWithPaywall))
        context.append(getCode("AD", todayTopLevelSections))
        context.append(getCode("AE", todayTopLevelSectionsByArticle))
        context.append(getCode("AF", todayPageViewsByArticleWithRegwall))
        context.append(getCode("BA", sevenDayPageViews))
        context.append(getCode("BB", sevenDayPageViewsByArticle))
        context.append(getCode("BC", sevenDayPageViewsByArticleWithPaywall))
        context.append(getCode("BD", sevenDayTopLevelSections))
        context.append(getCode("BE", sevenDayTopLevelSectionsByArticle))
        context.append(getCode("BF", sevenDayVisitCount))
        context.append(getCode("BG", sevenDayPageViewsByArticleWithRegwall))
        context.append(getCode("CA", twentyEightDayPageViews))
        context.append(getCode("CB", twentyEightDayPageViewsByArticle))
        context.append(getCode("CC", twentyEightDayPageViewsByArticleWithPaywall))
        context.append(getCode("CD", twentyEightDayTopLevelSections))
        context.append(getCode("CE", twentyEightDayTopLevelSectionsByArticle))
        context.append(getCode("CF", twentyEightDayVisitCount))
        context.append(getCode("CG", twentyEightDayPageViewsByArticleWithRegwall))
        return context.toString()
    }

    private fun getCode(featureCode: String, value: Any): String {
        if (value is Int) {
            return "$featureCode:${value.toString().padStart(2, '0')}"
        }
        return "$featureCode:$value"
    }

    fun getEncodedInputs(): String {
        val inputs = ArrayList<String>()

        // Experiments assignedGroup (HB)
        assignedGroup?.let {
            val group = ExperimentGroup.valueOf(it.uppercase()).code
            inputs.add(getCode("HB", group))
        }

        // Visitor
        inputs.add(getCode("FA", visitorType.code))

        // Referrer

        referrer?.let { referrerMedium -> inputs.add(getCode("DC", referrerMedium.code)) }

        referrerData?.let { rd ->
            inputs.add(getCode("DE", rd.medium.code))
            rd.source?.let { source -> inputs.add(getCode("DF", source.code)) }
            rd.channel?.let { channel -> inputs.add(getCode("DG", channel.code)) }
        }

        // Device
        os?.let { inputs.add(getCode("EA", it)) }
        type?.let { inputs.add(getCode("EB", it.code)) }
        viewer?.let { inputs.add("EC:${it}") }

        // Timezone and Hour of Day
        inputs.add(getCode("DD", hourOfDay))
        inputs.add(getCode("DB", timeZone))


        // Visitor Session
        visitor?.session?.let { session ->
            session.campaignName?.let { cn ->
                inputs.add(getCode("GA", cn))
            }
            session.referrerDomain?.let { rd ->
                inputs.add(getCode("GB", rd))
            }
            session.referrerMedium?.let { rm ->
                inputs.add(getCode("GC", rm.value))
            }
            session.referrerSource?.let { rs ->
                inputs.add(getCode("GD", rs.value))
            }
            session.referrerChannel?.let { rc ->
                inputs.add(getCode("GE", rc.value))
            }
        }

        return inputs.joinToString("|")
    }

    private fun encodeProperties(properties: Map<String, Any?>?, codes: List<PropertyCodes>?): String? {
        if (properties == null || codes == null) {
            return null
        }
        val encodedProperties = ArrayList<String>()
        codes.forEach { propertyCodes ->
            val value = properties[propertyCodes.name] ?: return@forEach
            encodedProperties.add("${propertyCodes.key}:$value")
        }
        return encodedProperties.joinToString("|")
    }

    fun getEncodedUserProperties(codes: List<PropertyCodes>?): String? {
        return encodeProperties(userProperties, codes)
    }

    fun getEncodedContentProperties(codes: List<PropertyCodes>?): String? {
        return encodeProperties(content?.properties, codes)
    }
}