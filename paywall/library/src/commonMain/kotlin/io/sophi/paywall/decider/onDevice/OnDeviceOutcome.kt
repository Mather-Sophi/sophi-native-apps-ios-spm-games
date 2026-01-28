package io.sophi.paywall.decider.onDevice

import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import kotlinx.serialization.Serializable

@Serializable
internal data class OnDeviceOutcome(
    val wallVisibility: WallVisibility,
    val wallType: WallType?,
    val score: Double?
)