package io.sophi.paywall.configuration

import kotlinx.serialization.Serializable


@Serializable
data class BonusParameters(
    val hourOfDay: Map<Int, Double>,
    val daysSinceLastVisit: Map<Int, Double>,
    val referrer: Map<String, Double>,
    val sevenDayPageViewsByArticle: Map<Int, Double>,
    val todayPageViewsByArticle: Map<Int, Double>,
    val todayTopLevelSections: Map<Int, Double>,
    val todayTopLevelSectionsByArticle: Map<Int, Double>,
    val viewer: Map<String, Double>,
    val sevenDaysStopRate: Map<Double, Double>,
    val todayStopRate: Map<Double, Double>,
    val deviceOSAndType: Map<String, Map<String, Double>>,
    val session: Map<String, Map<String, Double>>,
)

@Serializable
data class Parameters(
    var avgScore: Double = 0.5,
    var avgBonus: Double = 0.1,
    var avgRegwallBonus: Double = 0.0,
    var isRandomized: Boolean = true,
    val visitorBonusParameters: Map<String, BonusParameters>,
    val visitorRegwallBonusParameters: BonusParameters
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
data class PropertyCodes(
    val key: String,
    val name: String,
    val type: String
)

@Serializable
data class Experience(
    val definition: String,
    val policy: ControlPolicy,
    val activeModels: List<ActiveModel>,
    val userPropertyCodes: List<PropertyCodes>? = null,
    val contentPropertyCodes: List<PropertyCodes>? = null
) {
    fun getModelById(modelId: String): ActiveModel? {
        return activeModels.find { model -> model.id.equals(modelId, ignoreCase = true) }
    }
}