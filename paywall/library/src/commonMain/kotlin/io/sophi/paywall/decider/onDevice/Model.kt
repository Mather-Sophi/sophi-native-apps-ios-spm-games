package io.sophi.paywall.decider.onDevice

import io.sophi.paywall.configuration.BonusParameters
import io.sophi.paywall.configuration.Parameters
import io.sophi.paywall.enums.VisitorType
import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.Context
import kotlin.math.round
import kotlin.random.Random

internal fun applyBonus(
    currentBonus: Double = 0.0,
    averageBonus: Double,
    context: Context,
    bonusParameters: BonusParameters
): Double {
    var totalBonus = currentBonus

    // 1 day
    totalBonus += averageBonus * (bonusParameters.todayPageViews?.get(context.todayPageViews) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.todayPageViewsByArticle?.get(context.todayPageViewsByArticle) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.todayPageViewsByArticleWithPaywall?.get(context.todayPageViewsByArticleWithPaywall)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.todayPageViewsByArticleWithRegwall?.get(context.todayPageViewsByArticleWithRegwall)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.todayTopLevelSections?.get(context.todayTopLevelSections) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.todayTopLevelSectionsByArticle?.get(context.todayTopLevelSectionsByArticle)
        ?: 0.0)

    // 7 days
    totalBonus += averageBonus * (bonusParameters.sevenDayPageViews?.get(context.sevenDayPageViews) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.sevenDayPageViewsByArticle?.get(context.sevenDayPageViewsByArticle)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.sevenDayPageViewsByArticleWithPaywall?.get(context.sevenDayPageViewsByArticleWithPaywall)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.sevenDayPageViewsByArticleWithRegwall?.get(context.sevenDayPageViewsByArticleWithRegwall)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.sevenDayTopLevelSections?.get(context.sevenDayTopLevelSections)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.sevenDayTopLevelSectionsByArticle?.get(context.sevenDayTopLevelSectionsByArticle)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.sevenDayVisitCount?.get(context.sevenDayVisitCount) ?: 0.0)

    // 28 days
    totalBonus += averageBonus * (bonusParameters.twentyEightDayPageViews?.get(context.twentyEightDayPageViews) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.twentyEightDayPageViewsByArticle?.get(context.twentyEightDayPageViewsByArticle)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.twentyEightDayPageViewsByArticleWithPaywall?.get(context.twentyEightDayPageViewsByArticleWithPaywall)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.twentyEightDayPageViewsByArticleWithRegwall?.get(context.twentyEightDayPageViewsByArticleWithRegwall)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.twentyEightDayTopLevelSections?.get(context.twentyEightDayTopLevelSections)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.twentyEightDayTopLevelSectionsByArticle?.get(context.twentyEightDayTopLevelSectionsByArticle)
        ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.twentyEightDayVisitCount?.get(context.twentyEightDayVisitCount)
        ?: 0.0)

    // visit
    totalBonus += averageBonus * (bonusParameters.daysSinceLastVisit?.get(context.daysSinceLastVisit) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.timeZone?.get(context.timeZone.lowercase()) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.referrer?.get(context.referrer?.value) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.hourOfDay?.get(context.hourOfDay) ?: 0.0)

    // device
    totalBonus += averageBonus * (bonusParameters.os?.get(context.os?.value) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.type?.get(context.type?.value) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.viewer?.get(context.viewer?.lowercase()) ?: 0.0)

    // user
    totalBonus += averageBonus * (bonusParameters.visitorType?.get(context.visitorType.value) ?: 0.0)
    totalBonus += averageBonus * (bonusParameters.assignedGroup?.get(context.assignedGroup?.lowercase()) ?: 0.0)

    // visitor session
    context.visitor?.session?.let { session ->
        totalBonus += averageBonus * (bonusParameters.sessionCampaignName?.get(session.campaignName?.lowercase())
            ?: 0.0)
        totalBonus += averageBonus * (bonusParameters.sessionReferrerDomain?.get(session.referrerDomain?.lowercase())
            ?: 0.0)
        totalBonus += averageBonus * (bonusParameters.sessionReferrerMedium?.get(session.referrerMedium?.value) ?: 0.0)
        totalBonus += averageBonus * (bonusParameters.sessionReferrerSource?.get(session.referrerSource?.value) ?: 0.0)
        totalBonus += averageBonus * (bonusParameters.sessionReferrerChannel?.get(session.referrerChannel?.value)
            ?: 0.0)
    }

    // referrer data
    context.referrerData?.let { referrerData ->
        totalBonus += averageBonus * (bonusParameters.referrerDataMedium?.get(referrerData.medium.value) ?: 0.0)
        totalBonus += averageBonus * (bonusParameters.referrerDataSource?.get(referrerData.source?.value) ?: 0.0)
        totalBonus += averageBonus * (bonusParameters.referrerDataChannel?.get(referrerData.channel?.value) ?: 0.0)
    }

    // user properties
    bonusParameters.userProperties?.forEach { (key, propertyMap) ->
        context.userProperties?.get(key)?.toString()?.let { value ->
            totalBonus += averageBonus * (propertyMap[value] ?: 0.0)
        }
    }

    return totalBonus
}

internal class Model(val parameters: Parameters) {
    var avgScore: Double = parameters.avgScore
    var avgBonus: Double = parameters.avgBonus
    var avgRegwallBonus: Double = parameters.avgRegwallBonus
    var isRandomized: Boolean = parameters.isRandomized
    var scoreRandomizationFactor: Double = parameters.scoreRandomizationFactor
    var bonus: Double = 0.0
    var regwallBonus: Double = 0.0
    var score: Double = 0.0
    var regwallScore: Double = 0.0

    internal fun predict(context: Context): List<OnDeviceOutcome> {
        val randomNumber = if (isRandomized) Random.nextDouble() else 0.5

        // Calculate Stop Rates
        val todayStopRate = round(
            ((context.todayPageViewsByArticleWithPaywall.toDouble() * 10) / context.todayPageViewsByArticle.toDouble()) / 10
        )

        val sevenDaysStopRate = round(
            ((context.sevenDayPageViewsByArticleWithPaywall.toDouble() * 10) / context.sevenDayPageViewsByArticle.toDouble()) / 10
        )

        val twentyEightDayStopRate = round(
            ((context.twentyEightDayPageViewsByArticleWithPaywall.toDouble() * 10) / context.twentyEightDayPageViewsByArticle.toDouble()) / 10
        )

        // Apply Paywall and Regwall Bonuses
        parameters.visitorPaywallBonusParameters[context.visitorType.value]?.let {
            bonus += avgBonus * (it.todayStopRate?.get(todayStopRate) ?: 0.0)
            bonus += avgBonus * (it.sevenDayStopRate?.get(sevenDaysStopRate) ?: 0.0)
            bonus += avgBonus * (it.twentyEightDayStopRate?.get(twentyEightDayStopRate) ?: 0.0)
            bonus = applyBonus(bonus, avgBonus, context, it)
        }

        regwallBonus = applyBonus(regwallBonus, avgRegwallBonus, context, parameters.regwallBonusParameters)


        // Calculate Scores
        score = avgScore + bonus
        regwallScore = avgScore + regwallBonus

        // Apply Randomization
        if (isRandomized) {
            score += (randomNumber - 0.5) * scoreRandomizationFactor
            regwallScore += (randomNumber - 0.5) * scoreRandomizationFactor
        }

        // Clamp Scores to [scoreBounds.min, scoreBounds.max]
        regwallScore = when {
            regwallScore <= 0.1 -> parameters.scoreBounds.min
            regwallScore >= 1 -> parameters.scoreBounds.max
            else -> regwallScore
        }

        score = when {
            score <= 0.1 -> parameters.scoreBounds.min
            score >= 1 -> parameters.scoreBounds.max
            else -> score
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