package io.sophi.paywall.decider.onDevice

import co.touchlab.kermit.Logger
import io.sophi.paywall.configuration.ControlPolicy
import io.sophi.paywall.enums.VisitorType
import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.Context
import io.sophi.paywall.models.Outcome
import io.sophi.paywall.models.WallDecision
import kotlin.math.roundToInt


class Policy(val controlPolicy: ControlPolicy) {
    internal fun evaluate(
        decision: WallDecision,
        context: Context,
        onDeviceOutcomes: List<OnDeviceOutcome>
    ): WallDecision {

        val paywallScore = onDeviceOutcomes.firstOrNull { it.wallType == WallType.PAYWALL }?.score
        val regwallScore = onDeviceOutcomes.firstOrNull { it.wallType == WallType.REGWALL }?.score

        if (paywallScore == null && regwallScore == null) {
            Logger.d("Returning fallback Decision since no scores are available")
            return decision.copy(
                outcome = getFallbackOutcome()
            )
        }

        val userThresholds = controlPolicy.thresholds.user

        val paywallThreshold: Double? = when (context.visitorType) {
            VisitorType.ANONYMOUS -> userThresholds.paywall?.anonymous
            else -> userThresholds.paywall?.registered
        }


        val regwallThreshold: Double? = when (context.visitorType) {
            VisitorType.ANONYMOUS -> userThresholds.regwall
            else -> null
        }

        val isPaywall =
            paywallThreshold?.let { threshold -> paywallScore?.let { score -> score >= threshold } ?: false } ?: false
        val isRegwall =
            regwallThreshold?.let { threshold -> regwallScore?.let { score -> score >= threshold } ?: false } ?: false

        // Trace
        val traceBuilder = StringBuilder()
        if (regwallThreshold != null && regwallScore != null) {
            addSegmentTrace(
                traceStringBuilder = traceBuilder,
                wallType = WallType.REGWALL,
                wallVisibility = if (isRegwall) WallVisibility.ALWAYS else WallVisibility.NEVER,
                score = regwallScore,
                threshold = regwallThreshold
            )
        }

        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.PAYWALL,
            wallVisibility = if (isPaywall) WallVisibility.ALWAYS else WallVisibility.NEVER,
            score = paywallScore,
            threshold = paywallThreshold
        )

        // Final Decision
        return decision.copy(
            outcome = when {
                isPaywall -> Outcome(wallType = WallType.PAYWALL, wallVisibility = WallVisibility.ALWAYS)
                isRegwall -> Outcome(wallType = WallType.REGWALL, wallVisibility = WallVisibility.ALWAYS)
                else -> Outcome(wallVisibility = WallVisibility.NEVER)
            },
            trace = traceBuilder.toString()
        )
    }
}

private fun getFallbackOutcome(): Outcome {
    return Outcome(wallVisibility = WallVisibility.NEVER)
}

internal fun addSegmentTrace(
    traceStringBuilder: StringBuilder,
    wallType: WallType,
    wallVisibility: WallVisibility,
    score: Double?,
    threshold: Double?,
) {
    if (threshold == null || score == null) return

    // First : TraceCode
    traceStringBuilder.append(wallType.traceCode)
    // Second : WallVisibility
    traceStringBuilder.append(wallVisibility.code)
    // Third : WallType
    traceStringBuilder.append(wallType.code.toString())
    // Fourth & Fifth : 2 digit-score
    traceStringBuilder.append((score.times(100)).roundToInt().let {
        if (it < 100) it.toString().padStart(2, '0') else "xx"
    })
    // Sixth & Seventh : 2 digit-threshold
    traceStringBuilder.append((threshold.times(100)).roundToInt().let {
        if (it < 100) it.toString().padStart(2, '0') else "xx"
    })
}
