package io.sophi.paywall.decider


import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.Outcome
import io.sophi.paywall.models.WallDecision

internal val FALLBACK_ONDEVICE_DECISION: Lazy<WallDecision> = lazy {
    WallDecision(
        outcome = Outcome(wallVisibility = WallVisibility.NEVER),
        trace = null,
        context = null,
        experimentsCode = null
    )
}