package io.sophi.paywall.decider.paywallApi

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.sophi.paywall.models.Context
import io.sophi.paywall.models.Settings
import io.sophi.paywall.models.WallDecision

internal fun ParametersBuilder.appendRequestParameters(context: Context, modelTrace: String?) {
    context.content?.id?.let { this.append("content.id", it) }
    context.content?.properties?.get("sectionCategory")?.let {
        this.append("page.sectionCategory", it as String)
    }
    context.content?.properties?.get("section")?.let {
        this.append("page.section", it as String)
    }
    context.visitorType.let { this.append("visitor.type", it.value) }
    context.os?.let { this.append("visitor.device.os", it.value) }
    context.type?.let { this.append("visitor.device.type", it.value) }
    context.viewer?.let { this.append("visitor.device.viewer", it) }
    context.referrer?.let { this.append("visitor.visit.referrer", it.value) }
    context.timeZone.let { this.append("visitor.visit.timezone", it) }

    // OD model trace
    modelTrace?.let { this.append("modelTrace", it) }

    context.userProperties?.forEach { (key, value) ->
        this.append("userProperties.$key", value.toString())
    }
}

class PaywallApiClient(
    host: String,
    settings: Settings,
    val client: HttpClient
) {
    private val endpoint: String = "${settings.paywallApiBaseUrl}/hosts/${host}/decisions/me"

    suspend fun getDecision(
        context: Context,
        onDeviceTrace: String? = null
    ): WallDecision? {
        return try {
            val response: HttpResponse = client.request(urlString = endpoint) {
                method = HttpMethod.Get
                url {
                    parameters.appendRequestParameters(
                        context = context,
                        modelTrace = onDeviceTrace
                    )
                    parameters.append("expand", "true")
                }
            }

            Logger.d("Paywall decision response status: ${response.bodyAsText()}")
            val decision = response.body<Response>()
            WallDecision(
                id = decision.id,
                createdAt = decision.createdAt,
                trace = decision.trace,
                outcome = decision.outcome
            )
        } catch (e: Exception) {
            Logger.e("Failed to get paywall decision: ${e.message}")
            null
        }
    }

}