package io.sophi.paywall.models

import io.sophi.paywall.enums.ReferrerChannel
import io.sophi.paywall.enums.ReferrerMedium
import io.sophi.paywall.enums.ReferrerSource
import io.sophi.paywall.enums.VisitorType
import kotlinx.serialization.Serializable

private fun toIntOrDefault(value: Any?, default: Int = 0): Int {
    if (value == null) return default
    return when (value) {
        is Int -> value
        is Number -> value.toInt()
        is String -> {
            try {
                value.toInt()
            } catch (_: Exception) {
                default
            }
        }

        else -> default
    }
}

/**
 * Class representing user dimensions for paywall analytics.
 * * @property todayPageViews Number of page views today.
 * * @property todayPageViewsByArticle Number of page views by article today.
 * * @property todayPageViewsByArticleWithPaywall Number of page views by article with paywall viewed today.
 * * @property todayPageViewsByArticleWithRegwall Number of page views by article with regwall viewed today.
 * * @property todayTopLevelSections Number of unique top-level sections viewed today.
 * * @property todayTopLevelSectionsByArticle Number of unique top-level sections by article viewed today.
 * * @property sevenDayPageViews Total page views in the last 7 days.
 * * @property sevenDayPageViewsByArticle Article page views over the past 7 days.
 * * @property sevenDayPageViewsByArticleWithPaywall Article page views with paywall over the past 7 days.
 * * @property sevenDayPageViewsByArticleWithRegwall Article page views with regwall over the past 7 days.
 * * @property sevenDayTopLevelSections Unique top-level sections viewed in the last 7 days.
 * * @property sevenDayTopLevelSectionsByArticle Unique sections for articles over 7 days.
 * * @property sevenDayVisitCount Number of distinct days the user visited in the past 7 days.
 * * @property twentyEightDayPageViews Total page views in the last 28 days.
 * * @property twentyEightDayPageViewsByArticle Article page views over the past 28 days.
 * * @property twentyEightDayPageViewsByArticleWithPaywall Article page views with paywall over the past 28 days.
 * * @property twentyEightDayPageViewsByArticleWithRegwall Article page views with regwall over the past 28 days.
 * * @property twentyEightDayTopLevelSections Unique top-level sections viewed in the last 28 days.
 * * @property twentyEightDayTopLevelSectionsByArticle Unique sections for articles over 28 days.
 * * @property twentyEightDayVisitCount Number of distinct days the user visited in the past 28 days.
 * * @property daysSinceLastVisit Number of days since the user's last visit. 0 for new users, 1-5 for returning users.
 * * @property visitorType Type of visitor based on authentication status, such as "anonymous" or "registered".
 * * @property visitor Additional Visitor instance object.
 * * @property timeZone User's time zone identifier (e.g., "America/New_York") IANA standard.
 * * @property referrer Page referrer information: The referrer URL.
 * * @property referrerData Page referrer information: Object containing referrer metadata.
 * * @property countryCode The user's country code.
 */
@Serializable
data class UserDimensions(
    var todayPageViews: Int = 0,
    var todayPageViewsByArticle: Int = 0,
    var todayPageViewsByArticleWithPaywall: Int = 0,
    var todayPageViewsByArticleWithRegwall: Int = 0,
    var todayTopLevelSections: Int = 0,
    var todayTopLevelSectionsByArticle: Int = 0,
    var sevenDayPageViews: Int = 0,
    var sevenDayPageViewsByArticle: Int = 0,
    var sevenDayPageViewsByArticleWithPaywall: Int = 0,
    var sevenDayPageViewsByArticleWithRegwall: Int = 0,
    var sevenDayTopLevelSections: Int = 0,
    var sevenDayTopLevelSectionsByArticle: Int = 0,
    var sevenDayVisitCount: Int = 0,
    var twentyEightDayPageViews: Int = 0,
    var twentyEightDayPageViewsByArticle: Int = 0,
    var twentyEightDayPageViewsByArticleWithPaywall: Int = 0,
    var twentyEightDayPageViewsByArticleWithRegwall: Int = 0,
    var twentyEightDayTopLevelSections: Int = 0,
    var twentyEightDayTopLevelSectionsByArticle: Int = 0,
    var twentyEightDayVisitCount: Int = 0,
    var daysSinceLastVisit: Int = 0,
    var visitorType: VisitorType = VisitorType.REGISTERED,
    var visitor: Visitor? = null,
    var timeZone: String? = null,
    var referrer: ReferrerMedium? = null,
    var referrerData: ReferrerData? = null
) {

    companion object Factory {
        fun fromMap(data: Map<String, Any?>): UserDimensions {
            val visitorType = try {
                VisitorType.valueOf((data["visitorType"] as? String)?.uppercase() ?: "REGISTERED")
            } catch (_: Exception) {
                VisitorType.REGISTERED
            }
            val dimension = UserDimensions(
                todayPageViews = toIntOrDefault(data["todayPageViews"]),
                todayPageViewsByArticle = toIntOrDefault(data["todayPageViewsByArticle"]),
                todayPageViewsByArticleWithPaywall = toIntOrDefault(data["todayPageViewsByArticleWithPaywall"]),
                todayPageViewsByArticleWithRegwall = toIntOrDefault(data["todayPageViewsByArticleWithRegwall"]),
                todayTopLevelSections = toIntOrDefault(data["todayTopLevelSections"]),
                todayTopLevelSectionsByArticle = toIntOrDefault(data["todayTopLevelSectionsByArticle"]),
                sevenDayPageViews = toIntOrDefault(data["sevenDayPageViews"]),
                sevenDayPageViewsByArticle = toIntOrDefault(data["sevenDayPageViewsByArticle"]),
                sevenDayPageViewsByArticleWithPaywall = toIntOrDefault(data["sevenDayPageViewsByArticleWithPaywall"]),
                sevenDayPageViewsByArticleWithRegwall = toIntOrDefault(data["sevenDayPageViewsByArticleWithRegwall"]),
                sevenDayTopLevelSections = toIntOrDefault(data["sevenDayTopLevelSections"]),
                sevenDayTopLevelSectionsByArticle = toIntOrDefault(data["sevenDayTopLevelSectionsByArticle"]),
                sevenDayVisitCount = toIntOrDefault(data["sevenDayVisitCount"]),
                twentyEightDayPageViews = toIntOrDefault(data["twentyEightDayPageViews"]),
                twentyEightDayPageViewsByArticle = toIntOrDefault(data["twentyEightDayPageViewsByArticle"]),
                twentyEightDayPageViewsByArticleWithPaywall = toIntOrDefault(data["twentyEightDayPageViewsByArticleWithPaywall"]),
                twentyEightDayPageViewsByArticleWithRegwall = toIntOrDefault(data["twentyEightDayPageViewsByArticleWithRegwall"]),
                twentyEightDayTopLevelSections = toIntOrDefault(data["twentyEightDayTopLevelSections"]),
                twentyEightDayTopLevelSectionsByArticle = toIntOrDefault(data["twentyEightDayTopLevelSectionsByArticle"]),
                twentyEightDayVisitCount = toIntOrDefault(data["twentyEightDayVisitCount"]),
                daysSinceLastVisit = toIntOrDefault(data["daysSinceLastVisit"]),
                visitorType = visitorType,
                timeZone = data["timeZone"] as? String,
                referrer = data["referrer"]?.let {
                    try {
                        ReferrerMedium.valueOf((it as String).uppercase())
                    } catch (_: Exception) {
                        null
                    }
                },
                visitor = Visitor.fromMap(data["visitor"]),
                referrerData = ReferrerData.fromMap(data["referrerData"]),
            )
            return dimension
        }
    }

    @Serializable
    data class Visitor(
        var session: Session? = null
    ) {
        companion object Factory {
            fun fromMap(optionalData: Any?): Visitor? {
                return optionalData?.let { data ->
                    when (data) {
                        is Map<*, *> -> Visitor(session = Session.fromMap(data["session"]))
                        else -> null
                    }
                }
            }
        }
    }

    /**
     * Session metrics represent the values that were captured at the beginning of a user session.
     *
     * A session is defined as a 30-minute period of continuous activity. If 30 minutes pass without
     * any interaction event, the current session expires and a new session begins. When a new session
     * starts, all values within the session class are refreshed with the current state at that moment.
     *
     * This means these metrics are snapshots of the state when the session was initiated, not
     * real-time values that update during the session.
     */
    @Serializable
    data class Session(
        var campaignName: String? = null,
        var referrerDomain: String? = null,
        var referrerMedium: ReferrerMedium? = null,
        var referrerSource: ReferrerSource? = null,
        var referrerChannel: ReferrerChannel? = null,
        // TODO: Explore native time
        var timestamp: String? = null
    ) {

        init {
            require(
                timestamp == null ||
                        Regex("""\d{4}-\d{2}-\d{2}T\d{2}:\d{2}(:\d{2}(\.\d{1,6})?)?(Z|[+-]\d{2}:\d{2})""").matches(
                            timestamp!!
                        )
            ) { "timestamp value '$timestamp' must be in valid ISO 8601 format. e.g. YYYY-MM-DDTHH:MM:SSZ or with timezone offset." }

        }

        companion object Factory {
            fun fromMap(optionalData: Any?): Session? {
                return optionalData?.let { data ->
                    when (data) {
                        is Map<*, *> -> Session(
                            campaignName = data["campaignName"] as? String,
                            referrerDomain = data["referrerDomain"] as? String,
                            referrerMedium = data["referrerMedium"]?.let {
                                try {
                                    ReferrerMedium.valueOf((it as String).uppercase())
                                } catch (_: Exception) {
                                    null
                                }
                            },
                            referrerSource = data["referrerSource"]?.let {
                                try {
                                    ReferrerSource.valueOf((it as String).uppercase())
                                } catch (_: Exception) {
                                    null
                                }
                            },
                            referrerChannel = data["referrerChannel"]?.let {
                                try {
                                    ReferrerChannel.valueOf((it as String).uppercase())
                                } catch (_: Exception) {
                                    null
                                }
                            },
                            timestamp = data["timestamp"] as? String
                        )

                        else -> null
                    }
                }
            }
        }

    }


    /** Referrer metadata associated with the user's visit for the current pageview.
     * This is updated with new values for each pageview.
     *
     * @property medium Page referrer information: The medium through which the user arrived (e.g., "search", "social", "direct").
     * @property source Page referrer information: The source of the referral (optional).
     * @property channel Page referrer information: The channel of the referral (optional).
     * @property utmParams Page referrer information: A map of UTM parameters associated with the referral (optional). Only utm_campaign, utm_medium, utm_source values are supported.
     */

    @Serializable
    data class ReferrerData(
        var medium: ReferrerMedium,
        var source: ReferrerSource? = null,
        var channel: ReferrerChannel? = null,
        var utmParams: Map<String, String>? = null
    ) {
        companion object Factory {
            fun fromMap(optionalData: Any?): ReferrerData? {
                return optionalData?.let { data ->
                    when (data) {
                        is Map<*, *> -> ReferrerData(
                            medium = data["medium"]?.let {
                                try {
                                    ReferrerMedium.valueOf((it as String).uppercase())
                                } catch (e: Exception) {
                                    throw IllegalArgumentException("Unable to infer referrerMedium value $it. Cause: $e")
                                }
                            } ?: throw IllegalArgumentException("ReferrerData medium is required"),
                            source = data["source"]?.let {
                                try {
                                    ReferrerSource.valueOf((it as String).uppercase())
                                } catch (_: Exception) {
                                    null
                                }
                            },
                            channel = data["channel"]?.let {
                                try {
                                    ReferrerChannel.valueOf((it as String).uppercase())
                                } catch (_: Exception) {
                                    null
                                }
                            },
                            utmParams = data["utmParams"]?.let {
                                when (it) {
                                    is Map<*, *> -> {
                                        it.map { entry ->
                                            entry.value?.let { value ->
                                                entry.key.toString() to value.toString()
                                            }
                                        }.filterNotNull().toMap()
                                    }

                                    else -> null
                                }
                            }
                        )

                        else -> null
                    }
                }
            }
        }
    }
}
