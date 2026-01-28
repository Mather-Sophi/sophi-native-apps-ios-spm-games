package io.sophi.app.test

data class TestResult(
    val testName: String,
    val passed: Boolean,
    val error: String? = null,
    val executionTimeMs: Long = 0,
    val debugInfo: String? = null
)

interface TestCase {
    val name: String
    suspend fun run(): TestResult
}
