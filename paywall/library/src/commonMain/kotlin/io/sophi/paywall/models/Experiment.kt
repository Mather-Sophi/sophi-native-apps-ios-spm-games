package io.sophi.paywall.models

import kotlinx.serialization.Serializable

@Serializable
data class Experiment(
    val experimentId: String?,
    val assignedGroup: String?
) {
    override fun toString(): String {
        return "$experimentId=$assignedGroup"
    }
}