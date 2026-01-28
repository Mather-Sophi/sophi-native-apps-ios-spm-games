package io.sophi.paywall.configuration

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.sophi.paywall.models.Settings
import kotlinx.coroutines.runBlocking

class ConfigurationClient(
    settings: Settings,
    private val httpClient: HttpClient
) {

    private val endpoint: String = settings.configurationUrl
    private val tenantHashSet = mapOf(
        "test.sophi.io" to "00000000",
        "www.reuters.com" to "3635666566"
    )

    fun getExperienceByHost(host: String): Experience {
        val experience = runBlocking {
            try {
                val tenantHash = tenantHashSet[host] ?: throw RuntimeException("Host $host is not configured")
                Logger.d("Fetching configuration for host $host")
                val response: HttpResponse =
                    httpClient.request(urlString = "${endpoint}/assets/demeter/1/stable/${tenantHash}/native.json") {
                        method = HttpMethod.Get
                    }
                return@runBlocking response.body<Experience>()
            } catch (ex: Exception) {
                Logger.e("Failed to fetch model configuration from ${endpoint}. Cause: ${ex.message}")
                return@runBlocking FALLBACK_EXPERIENCE.value
            }
        }
        Logger.d("Experience loaded with configuration: $experience")
        return experience
    }
}