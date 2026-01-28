package io.sophi.paywall.utils

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.sophi.paywall.models.Settings
import kotlinx.serialization.json.Json
import kotlin.concurrent.Volatile


object HttpClientProvider {
    @Volatile
    var client: HttpClient? = null

    // TODO: Consider closing a client if already exist and create a new one when settings change
    fun getClient(settings: Settings): HttpClient {
        if (client != null) {
            Logger.d("Reusing existing HttpClient instance")
            return client!!
        }
        client = HttpClient {
            install(HttpTimeout) {
                requestTimeoutMillis = settings.apiTimeoutInMilliSeconds
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(HttpCache)

            expectSuccess = true
        }

        return client!!
    }
}