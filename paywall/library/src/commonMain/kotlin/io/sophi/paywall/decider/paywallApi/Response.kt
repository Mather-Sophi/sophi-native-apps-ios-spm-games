package io.sophi.paywall.decider.paywallApi

import io.sophi.paywall.models.Outcome
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Response(
    val id: String,
    val outcome: Outcome,
    val trace: String,
    val createdAt: String,
    @SerialName("context")
    val jsonContext: JsonObject? = null,
) {
    fun getContextAsQueryString(): String {
        if (jsonContext == null) return ""
        val flatMap = flattenJsonObject(jsonContext)
        return flatMap.entries.joinToString("&") { "${it.key}=${it.value}" }
    }

    val context: Map<String, String>
        get() {
            if (jsonContext == null) return emptyMap()
            return jsonContext.toMap().mapValues { it.value.toString() }
        }

    private fun flattenJsonObject(
        json: JsonObject,
        parentKey: String = "",
        result: MutableMap<String, String> = mutableMapOf()
    ): Map<String, String> {
        for ((key, value) in json) {
            val fullKey = if (parentKey.isEmpty()) key else "$parentKey.$key"
            when (value) {
                is kotlinx.serialization.json.JsonPrimitive -> result[fullKey] = value.content
                is JsonObject -> flattenJsonObject(value, fullKey, result)
                else -> result[fullKey] = value.toString()
            }
        }
        return result
    }
}
