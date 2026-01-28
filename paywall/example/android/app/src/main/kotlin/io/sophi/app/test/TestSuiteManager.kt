package io.sophi.app.test

object TestSuiteManager {
    private val testCases = mutableListOf<TestCase>()

    fun registerTest(testCase: TestCase) {
        testCases.add(testCase)
    }

    fun getAllTests(): List<TestCase> {
        return testCases.toList()
    }

    suspend fun runAllTests(): List<TestResult> {
        return testCases.map { testCase ->
            val startTime = System.currentTimeMillis()
            try {
                val result = testCase.run()
                val executionTime = System.currentTimeMillis() - startTime
                result.copy(executionTimeMs = executionTime)
            } catch (e: Exception) {
                TestResult(
                    testName = testCase.name,
                    passed = false,
                    error = e.message ?: "Unknown error",
                    executionTimeMs = System.currentTimeMillis() - startTime
                )
            }
        }
    }
}
