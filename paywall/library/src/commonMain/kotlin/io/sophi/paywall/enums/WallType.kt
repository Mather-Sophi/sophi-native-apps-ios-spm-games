package io.sophi.paywall.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WallType(val value: String, val code: Int, val traceCode: String) {
    @SerialName(value = "nowall")
    NOWALL("nowall", 0, ""),

    @SerialName("paywall")
    PAYWALL("paywall", 1, "d"),

    @SerialName("regwall")
    REGWALL("regwall", 2, "b");
}