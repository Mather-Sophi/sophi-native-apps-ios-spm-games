package io.sophi.paywall.decider.onDevice

import co.touchlab.kermit.Logger
import io.sophi.paywall.configuration.Experience
import io.sophi.paywall.configuration.FALLBACK_PARAMETERS
import io.sophi.paywall.decider.FALLBACK_ONDEVICE_DECISION
import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.Context
import io.sophi.paywall.models.Outcome
import io.sophi.paywall.models.WallDecision


class OnDeviceClient(configuration: Experience) {

    private var model: Model = Model(configuration.getModelById("on-device")?.parameters ?: FALLBACK_PARAMETERS.value)
    private var policy: Policy = Policy(configuration.policy)

    fun getDecision(
        context: Context
    ): WallDecision {

        try {
            val outcomes = model.predict(context)
            Logger.d("OnDevice generated following outcomes: $outcomes")

            val decision = WallDecision(
                outcome = Outcome(wallVisibility = WallVisibility.NEVER)
            )

            return policy.evaluate(decision, context, outcomes)

        } catch (ex: Exception) {
            Logger.e("Failed to get decision from on-device model. Cause ${ex.message}")
            return FALLBACK_ONDEVICE_DECISION.value
        }
    }
}
