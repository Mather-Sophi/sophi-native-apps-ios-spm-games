package io.sophi.paywall.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WallVisibility(val value: String, val code: Int) {
    @SerialName("never")
    NEVER("never", 0),

    @SerialName("always")
    ALWAYS("always", 1),

    @SerialName("meter")
    METER("meter", 2);
}