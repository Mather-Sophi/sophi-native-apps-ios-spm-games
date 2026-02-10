package io.sophi.paywall.models

import io.sophi.paywall.utils.generateUUID
import io.sophi.paywall.utils.getCurrentISOTimestamp
import kotlinx.serialization.Serializable


@Serializable
data class WallDecision(
    val id: String = generateUUID(),
    val createdAt: String = getCurrentISOTimestamp(),
    val trace: String? = null,
    val context: String? = null,
    val inputs: String? = null,
    val outcome: Outcome,
    val searchParams: String? = null,
    val experimentsCode: String? = null,
    val paywallScore: Int? = null,
    val userProperties: String? = null,
    val contentProperties: String? = null
) {

    fun copy(
        trace: String? = this.trace,
        context: String? = this.context,
        inputs: String? = this.inputs,
        outcome: Outcome = this.outcome,
        searchParams: String? = this.searchParams,
        experimentsCode: String? = this.experimentsCode,
        paywallScore: Int? = this.paywallScore,
        userProperties: String? = this.userProperties,
        contentProperties: String? = this.contentProperties
    ): WallDecision {
        return WallDecision(
            id,
            createdAt,
            trace,
            context,
            inputs,
            outcome,
            searchParams,
            experimentsCode,
            paywallScore,
            userProperties,
            contentProperties
        )
    }

}