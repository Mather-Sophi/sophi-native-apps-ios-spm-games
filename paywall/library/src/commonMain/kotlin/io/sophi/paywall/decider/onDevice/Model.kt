package io.sophi.paywall.decider.onDevice

import io.sophi.paywall.configuration.Parameters
import io.sophi.paywall.enums.VisitorType
import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.Context
import kotlin.math.round
import kotlin.random.Random


internal class Model(val parameters: Parameters) {
    var avgScore: Double = parameters.avgScore
    var avgBonus: Double = parameters.avgBonus
    var avgRegwallBonus: Double = parameters.avgRegwallBonus
    var isRandomized: Boolean = parameters.isRandomized
    var bonus: Double = 0.0
    var regwallBonus: Double = 0.0
    var score: Double = 0.0
    var regwallScore: Double = 0.0
    internal fun predict(context: Context): List<OnDeviceOutcome> {
        val randomNumber = if (isRandomized) Random.nextDouble() else 0.5

        val todayStopRate = round(
            ((context.todayPageViewsByArticleWithPaywall.toDouble() * 10) / context.todayPageViewsByArticle.toDouble()) / 10
        )

        val sevenDaysStopRate = round(
            ((context.sevenDayPageViewsByArticleWithPaywall.toDouble() * 10) / context.sevenDayPageViewsByArticle.toDouble()) / 10
        )

        parameters.visitorBonusParameters[context.visitorType.value]?.let {
            bonus += avgBonus * (it.daysSinceLastVisit[context.daysSinceLastVisit] ?: 0.0)
            bonus += avgBonus * (it.hourOfDay[context.hourOfDay] ?: 0.0)
            bonus += avgBonus * (it.referrer[context.referrer?.value] ?: 0.0)
            bonus += avgBonus * (it.sevenDayPageViewsByArticle[context.sevenDayPageViewsByArticle] ?: 0.0)
            bonus += avgBonus * (it.todayPageViewsByArticle[context.todayPageViews] ?: 0.0)
            bonus += avgBonus * (it.todayTopLevelSections[context.todayTopLevelSections] ?: 0.0)
            bonus += avgBonus * (it.todayTopLevelSectionsByArticle[context.todayTopLevelSectionsByArticle] ?: 0.0)
            bonus += avgBonus * (it.viewer[context.viewer ?: ""] ?: 0.0)
            bonus += avgBonus * (it.sevenDaysStopRate[sevenDaysStopRate] ?: 0.0)
            bonus += avgBonus * (it.todayStopRate[todayStopRate] ?: 0.0)
            it.deviceOSAndType[context.os ?: ""]?.let { osParameters ->
                bonus += avgBonus * (osParameters[context.type ?: ""] ?: 0.0)
            }
            context.visitor?.session?.let { visitorSession ->
                it.session["referrerDomain"]?.let { referrerDomainParameters ->
                    bonus += avgBonus * (referrerDomainParameters[visitorSession.referrerDomain ?: ""] ?: 0.0)
                }
            }
        }

        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.daysSinceLastVisit[context.daysSinceLastVisit]
            ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.hourOfDay[context.hourOfDay] ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.referrer[context.referrer?.value]
            ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.sevenDayPageViewsByArticle[context.sevenDayPageViewsByArticle]
            ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.todayPageViewsByArticle[context.todayPageViews]
            ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.todayTopLevelSections[context.todayTopLevelSections]
            ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.todayTopLevelSectionsByArticle[context.todayTopLevelSectionsByArticle]
            ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.viewer[context.viewer ?: ""] ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.sevenDaysStopRate[sevenDaysStopRate]
            ?: 0.0)
        regwallBonus += avgRegwallBonus * (parameters.visitorRegwallBonusParameters.todayStopRate[todayStopRate] ?: 0.0)
        parameters.visitorRegwallBonusParameters.deviceOSAndType[context.os ?: ""]?.let { osParameters ->
            regwallBonus += avgRegwallBonus * (osParameters[context.type ?: ""] ?: 0.0)
        }
        /**
         * if ((type_and_os === "mobileandroid" || type_and_os === "tabletandroid") && args.referrer === "search") {
         *       bonus += -0.843 * avg_bonus;
         *     } else {
         *       bonus += 0.313 * avg_bonus;
         *     }
         *     if (os === 'ios' && args.referrerData.medium === "search" && args.referrerData.source === "google" && args.referrerData.channel === "discover") {
         *       bonus += 0.052 * avg_bonus;
         *     } else {
         *       bonus += -0.001 * avg_bonus;
         *     }
         */

        score = avgScore + bonus
        regwallScore = avgScore + regwallBonus

        if (isRandomized) {
            score += (randomNumber - 0.5) * 0.03
            regwallScore += (randomNumber - 0.5) * 0.03
        }

        if (regwallScore <= 0.01) regwallScore = 0.1
        else if (regwallScore <= 0.02) regwallScore = 0.2
        else if (regwallScore >= 1) regwallScore = 0.9

        if (score <= 0.01) score = 0.1
        else if (score <= 0.02) score = 0.2
        else if (score >= 1) score = 1.0


        if (context.visitorType == VisitorType.ANONYMOUS && context.visitor?.session?.referrerDomain == "android-app:") {
            score = 0.01
        }

        when (context.visitorType) {
            VisitorType.ANONYMOUS -> {
                return listOf(
                    OnDeviceOutcome(
                        score = regwallScore,
                        wallVisibility = WallVisibility.ALWAYS,
                        wallType = WallType.REGWALL
                    ),
                    OnDeviceOutcome(
                        score = score,
                        wallVisibility = WallVisibility.ALWAYS,
                        wallType = WallType.PAYWALL
                    )
                )
            }

            else -> {
                return listOf(
                    OnDeviceOutcome(
                        score = score,
                        wallVisibility = WallVisibility.ALWAYS,
                        wallType = WallType.PAYWALL
                    )
                )
            }
        }
    }
}