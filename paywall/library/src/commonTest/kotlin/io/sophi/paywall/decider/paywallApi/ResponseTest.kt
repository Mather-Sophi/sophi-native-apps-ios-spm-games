package io.sophi.paywall.decider.paywallApi

import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import io.sophi.paywall.models.Outcome
import kotlinx.serialization.json.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ResponseTest {
    @Test
    fun `context converted to query string correctly`() {
        val context = buildJsonObject {
            put("content", buildJsonObject {
                put("id", JsonPrimitive("72322287007"))
            })
            put("visitor", buildJsonObject {
                put("device", buildJsonObject {
                    put("type", JsonPrimitive("mobile"))
                    put("os", JsonPrimitive("android"))
                    put("viewer", JsonPrimitive("native"))
                })
                put("pageViewByArticleCount", JsonPrimitive(2))
            })
            put("expand", JsonPrimitive(true))
        }

        val response = Response(
            id = "5e76084f-3585-47c0-8eb7-0be0f7d87b15",
            outcome = Outcome(
                wallType = WallType.PAYWALL,
                wallVisibility = WallVisibility.ALWAYS,
            ),
            trace = "z114850e114850v000001",
            createdAt = "2025-12-09T15:25:01",
            jsonContext = context
        )

        assertEquals(
            "content.id=72322287007&visitor.device.type=mobile&visitor.device.os=android&visitor.device.viewer=native&visitor.pageViewByArticleCount=2&expand=true",
            response.getContextAsQueryString()
        )
    }

    @Test
    fun `response deserialized from JSON correctly`() {
        val jsonResponse = """
        {
            "id": "5e76084f-3585-47c0-8eb7-0be0f7d87b15",
            "outcome": {
                "wallType": "paywall",
                "wallVisibility": "always"
            },
            "trace": "z114850e114850v000001",
            "createdAt": "2025-12-09T15:25:01",
            "context": {
                "content": {
                    "id": "72322287007"
                },
                "visitor": {
                    "device": {
                        "type": "mobile",
                        "os": "android",
                        "viewer": "native"
                    },
                    "pageViewByArticleCount": 2
                },
                "expand": true
            }
        }
        """.trimIndent()

        val response = Json.decodeFromString<Response>(jsonResponse)

        assertEquals("5e76084f-3585-47c0-8eb7-0be0f7d87b15", response.id)
        assertEquals(WallType.PAYWALL, response.outcome.wallType)
        assertEquals(WallVisibility.ALWAYS, response.outcome.wallVisibility)
        assertEquals("z114850e114850v000001", response.trace)
        assertEquals("2025-12-09T15:25:01", response.createdAt)
        assertEquals("72322287007", response.jsonContext?.get("content")?.jsonObject?.get("id")?.jsonPrimitive?.content)
    }
}
