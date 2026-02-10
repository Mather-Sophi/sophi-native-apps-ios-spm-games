package io.sophi.paywall.decider.onDevice

import io.sophi.paywall.enums.WallType
import io.sophi.paywall.enums.WallVisibility
import kotlin.test.Test
import kotlin.test.assertEquals

class PolicyTest {

    @Test
    fun testAddSegmentTraceWithPaywallAlways() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.ALWAYS,
            score = 0.75,
            threshold = 0.50
        )

        assertEquals("d117550", traceBuilder.toString())

    }

    @Test
    fun testAddSegmentTraceWithPaywallNever() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.NEVER,
            score = 0.05,
            threshold = 0.50
        )

        assertEquals("d010550", traceBuilder.toString())
    }

    @Test
    fun testAddSegmentTraceWithScoreOverflow() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.ALWAYS,
            score = 1.5,
            threshold = 0.50
        )

        assertEquals("d11xx50", traceBuilder.toString())
    }

    @Test
    fun testAddSegmentTraceWithThresholdOverflow() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.ALWAYS,
            score = 0.5,
            threshold = 1.0
        )

        assertEquals("d1150xx", traceBuilder.toString())
    }

    @Test
    fun testAddSegmentTraceWithNullScore() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.ALWAYS,
            score = null,
            threshold = 0.50
        )

        assertEquals("", traceBuilder.toString()) // Nothing appended when score is null
    }

    @Test
    fun testAddSegmentTraceWithNullThreshold() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.REGWALL,
            wallVisibility = WallVisibility.ALWAYS,
            score = 0.85,
            threshold = null
        )
        assertEquals("", traceBuilder.toString()) // Nothing appended when score is null
    }

    @Test
    fun testAddSegmentTraceWithRegwall() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.REGWALL,
            wallVisibility = WallVisibility.ALWAYS,
            score = 0.60,
            threshold = 0.40
        )

        assertEquals("b126040", traceBuilder.toString())
    }

    @Test
    fun testAddSegmentTraceWhenCalledForMultipleDecisions() {
        val traceBuilder = StringBuilder()
        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.PAYWALL,
            wallVisibility = WallVisibility.NEVER,
            score = 0.75,
            threshold = 0.50
        )

        addSegmentTrace(
            traceStringBuilder = traceBuilder,
            wallType = WallType.REGWALL,
            wallVisibility = WallVisibility.NEVER,
            score = 0.0,
            threshold = 0.50
        )

        assertEquals("d017550b020050", traceBuilder.toString())
    }
}
