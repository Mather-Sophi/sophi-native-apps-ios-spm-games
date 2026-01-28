package io.sophi.paywall.models

import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import kotlinx.serialization.Serializable

@Serializable
data class Outcome(
    val wallType: WallType? = null,
    val wallVisibility: WallVisibility,
) {
    val wallTypeCode: Int?
        get() = wallType?.code
    val wallVisibilityCode: Int
        get() = wallVisibility.code
}