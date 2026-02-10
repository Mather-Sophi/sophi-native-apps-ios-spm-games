package io.sophi.paywall.configuration

import kotlinx.serialization.Serializable


@Serializable
data class BonusParameters(
    // Stop Rates
    var todayStopRate: Map<Double, Double>? = null,
    var sevenDayStopRate: Map<Double, Double>? = null,
    var twentyEightDayStopRate: Map<Double, Double>? = null,

    // 1 day
    var todayPageViews: Map<Int, Double>? = null,
    var todayPageViewsByArticle: Map<Int, Double>? = null,
    var todayPageViewsByArticleWithPaywall: Map<Int, Double>? = null,
    var todayPageViewsByArticleWithRegwall: Map<Int, Double>? = null,
    var todayTopLevelSections: Map<Int, Double>? = null,
    var todayTopLevelSectionsByArticle: Map<Int, Double>? = null,

    // 7 days
    var sevenDayPageViews: Map<Int, Double>? = null,
    var sevenDayPageViewsByArticle: Map<Int, Double>? = null,
    var sevenDayPageViewsByArticleWithPaywall: Map<Int, Double>? = null,
    var sevenDayPageViewsByArticleWithRegwall: Map<Int, Double>? = null,
    var sevenDayTopLevelSections: Map<Int, Double>? = null,
    var sevenDayTopLevelSectionsByArticle: Map<Int, Double>? = null,
    var sevenDayVisitCount: Map<Int, Double>? = null,

    // 28 days
    var twentyEightDayPageViews: Map<Int, Double>? = null,
    var twentyEightDayPageViewsByArticle: Map<Int, Double>? = null,
    var twentyEightDayPageViewsByArticleWithPaywall: Map<Int, Double>? = null,
    var twentyEightDayPageViewsByArticleWithRegwall: Map<Int, Double>? = null,
    var twentyEightDayTopLevelSections: Map<Int, Double>? = null,
    var twentyEightDayTopLevelSectionsByArticle: Map<Int, Double>? = null,
    var twentyEightDayVisitCount: Map<Int, Double>? = null,

    // visit
    var daysSinceLastVisit: Map<Int, Double>? = null,
    var timeZone: Map<String, Double>? = null,
    var hourOfDay: Map<Int, Double>? = null,


    // referrer
    var referrer: Map<String, Double>? = null,
    var referrerDataMedium: Map<String, Double>? = null,
    val referrerDataSource: Map<String, Double>? = null,
    val referrerDataChannel: Map<String, Double>? = null,

    // device
    var os: Map<String, Double>? = null,
    var type: Map<String, Double>? = null,
    var viewer: Map<String, Double>? = null,

    // user
    var visitorType: Map<String, Double>? = null,
    var sessionCampaignName: Map<String, Double>? = null,
    var sessionReferrerDomain: Map<String, Double>? = null,
    var sessionReferrerMedium: Map<String, Double>? = null,
    var sessionReferrerSource: Map<String, Double>? = null,
    var sessionReferrerChannel: Map<String, Double>? = null,
    var assignedGroup: Map<String, Double>? = null,
    var userProperties: Map<String, Map<String, Double>>? = null,
)

@Serializable
data class ScoreBounds(
    val min: Double = 0.01,
    val max: Double = 0.99
)

@Serializable
data class Parameters(
    var avgScore: Double = 0.5,
    var avgBonus: Double = 0.1,
    var avgRegwallBonus: Double = 0.1,
    var isRandomized: Boolean = true,
    val scoreRandomizationFactor: Double = 0.03,
    val scoreBounds: ScoreBounds = ScoreBounds(),
    val visitorPaywallBonusParameters: Map<String, BonusParameters>,
    val regwallBonusParameters: BonusParameters
)

@Serializable
class PaywallThresholds(
    val anonymous: Double? = 0.5,
    val registered: Double? = 0.5
)

@Serializable
class UserThresholds(
    val paywall: PaywallThresholds? = PaywallThresholds(),
    val regwall: Double? = 1.0
)

@Serializable
data class Thresholds(
    val user: UserThresholds = UserThresholds()
)

@Serializable
data class ControlPolicy(
    val thresholds: Thresholds = Thresholds()
)

@Serializable
data class ActiveModel(
    val id: String,
    val parameters: Parameters
)

@Serializable
data class PropertyCodesMap(
    val key: String,
    val name: String,
    val type: String
)

@Serializable
data class PropertyCodes(
    val user: List<PropertyCodesMap>? = null,
    val content: List<PropertyCodesMap>? = null
)

@Serializable
data class Experience(
    val definition: String,
    val policy: ControlPolicy,
    val activeModels: List<ActiveModel>,
    val propertyCodes: PropertyCodes? = null
) {
    fun getModelById(modelId: String): ActiveModel? {
        return activeModels.find { model -> model.id.equals(modelId, ignoreCase = true) }
    }
}