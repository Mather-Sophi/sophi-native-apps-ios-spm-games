package io.sophi.paywall.models

import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class OutcomeTest {

    val noWallDecision: Outcome = Outcome(
        wallVisibility = WallVisibility.NEVER,
        wallType = null
    )

    val paywallDecision = Outcome(
        wallVisibility = WallVisibility.ALWAYS,
        wallType = WallType.PAYWALL
    )

    val regwallDecision = Outcome(
        wallVisibility = WallVisibility.ALWAYS,
        wallType = WallType.REGWALL
    )

    val meterDecision = Outcome(
        wallVisibility = WallVisibility.METER,
        wallType = WallType.PAYWALL
    )

    @Test
    fun testSerializationWithNoWallDecision() {
        val decisionJson = Json.encodeToString(noWallDecision)

        val outcome = Json.decodeFromString<Outcome>(decisionJson)
        assertEquals("never", outcome.wallVisibility.value)
        assertEquals(0, outcome.wallVisibilityCode)
        assertEquals(null, outcome.wallType)
        assertEquals(null, outcome.wallTypeCode)
    }

    @Test
    fun testSerializationWithPaywallDecision() {
        val decisionJson = Json.encodeToString(paywallDecision)

        val outcome = Json.decodeFromString<Outcome>(decisionJson)
        assertEquals("always", outcome.wallVisibility.value)
        assertEquals(1, outcome.wallVisibilityCode)
        assertEquals("paywall", outcome.wallType?.value)
        assertEquals(1, outcome.wallTypeCode)
    }

    @Test
    fun testSerializationWithRegwallDecision() {
        val decisionJson = Json.encodeToString(regwallDecision)

        val outcome = Json.decodeFromString<Outcome>(decisionJson)
        assertEquals("always", outcome.wallVisibility.value)
        assertEquals(1, outcome.wallVisibilityCode)
        assertEquals("regwall", outcome.wallType?.value)
        assertEquals(2, outcome.wallTypeCode)
    }

    @Test
    fun testSerializationWithMeterDecision() {
        val decisionJson = Json.encodeToString(meterDecision)

        val outcome = Json.decodeFromString<Outcome>(decisionJson)
        assertEquals("meter", outcome.wallVisibility.value)
        assertEquals(2, outcome.wallVisibilityCode)
        assertEquals("paywall", outcome.wallType?.value)
        assertEquals(1, outcome.wallTypeCode)
    }

}