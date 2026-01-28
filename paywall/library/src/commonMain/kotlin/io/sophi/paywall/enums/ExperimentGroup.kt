package io.sophi.paywall.enums

enum class ExperimentGroup(val value: String, val code: Int) {
    CONTROL("control", 1),
    VARIANT("variant", 2),
    HOLDOUT("holdout", 3);
}