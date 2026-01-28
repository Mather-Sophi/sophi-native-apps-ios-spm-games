package io.sophi.paywall.models

data class Settings(val map: Map<String, String?>) {
    val apiTimeoutInMilliSeconds: Long = map["apiTimeoutInMilliSeconds"]?.toLong() ?: 1000
    val paywallApiBaseUrl: String = map["paywallApiBaseUrl"] ?: "https://paywall.sophi.io"
    val configurationUrl: String = map["configurationUrl"] ?: " https://cdn.sophi.io"
}