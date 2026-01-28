package io.sophi.paywall.models

import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WallDecisionsTest {

    var experimentId: String? = null
    var assignedGroup: String? = null

    @BeforeTest
    fun setup() {
        experimentId = null
        assignedGroup = null
    }

    @Test
    fun testWallDecisionWhenPaywall() {
        val outcome = Outcome(
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.ALWAYS,
        )

        experimentId = "pw:article:test5_ab"
        assignedGroup = "control"

        // Creating experiment on the fly to mimic WallDecision creation by PaywallApiClient or OnDeviceClient
        val wallDecision = WallDecision(
            id = "decision123",
            createdAt = "2024-01-01T00:00:00Z",
            trace = "b004441d118265",
            context = "DA01AA04AB00AC00BA15BB15BC15AD01AE00BD04BE03BF03AF00BG00CA17CB02CC02CD04CE02CF05CG00",
            inputs = "FA:01|DC:02|DE:02|EA:ios|EB:mobile|DD:16|DB:America/Toronto|GC:direct",
            outcome = outcome,
            searchParams = "modelTrace=b004441d118265&visitorType=registered",
            experimentsCode = experimentId?.let {
                Experiment(
                    experimentId = experimentId,
                    assignedGroup = assignedGroup
                ).toString()
            }
        )

        assertEquals("decision123", wallDecision.id)
        assertEquals("2024-01-01T00:00:00Z", wallDecision.createdAt)
        assertEquals("b004441d118265", wallDecision.trace)
        assertEquals(
            "DA01AA04AB00AC00BA15BB15BC15AD01AE00BD04BE03BF03AF00BG00CA17CB02CC02CD04CE02CF05CG00",
            wallDecision.context
        )
        assertEquals("FA:01|DC:02|DE:02|EA:ios|EB:mobile|DD:16|DB:America/Toronto|GC:direct", wallDecision.inputs)
        assertEquals(
            "modelTrace=b004441d118265&visitorType=registered",
            wallDecision.searchParams
        )
        assertEquals("paywall", wallDecision.outcome.wallType?.value)
        assertEquals(1, wallDecision.outcome.wallTypeCode)
        assertEquals("always", wallDecision.outcome.wallVisibility.value)
        assertEquals(1, wallDecision.outcome.wallVisibilityCode)
        assertEquals("pw:article:test5_ab=control", wallDecision.experimentsCode)
    }

    @Test
    fun testWallDecisionWhenNoWall() {
        val outcome = Outcome(
            wallType = null,
            wallVisibility = WallVisibility.NEVER,
        )

        val wallDecision = WallDecision(
            id = "decision123",
            createdAt = "2024-01-01T00:00:00Z",
            trace = "b004441d118265",
            context = "DA01AA04AB00AC00BA15BB15BC15AD01AE00BD04BE03BF03AF00BG00CA17CB02CC02CD04CE02CF05CG00",
            inputs = "FA:01|DC:02|DE:02|EA:ios|EB:mobile|DD:16|DB:America/Toronto|GC:direct",
            outcome = outcome,
            searchParams = "modelTrace=b004441d118265&visitorType=registered",
            experimentsCode = experimentId?.let {
                Experiment(
                    experimentId = experimentId,
                    assignedGroup = assignedGroup
                ).toString()
            }
        )

        assertNull(wallDecision.outcome.wallType)
        assertNull(wallDecision.outcome.wallTypeCode)
        assertEquals("never", wallDecision.outcome.wallVisibility.value)
        assertEquals(0, wallDecision.outcome.wallVisibilityCode)
        assertNull(wallDecision.experimentsCode)
    }
}
