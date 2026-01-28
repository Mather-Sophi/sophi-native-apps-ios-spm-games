package io.sophi.app.test

import io.sophi.app.decision.DeciderProvider
import io.sophi.app.repository.NativeUserDimensionRepository
import io.sophi.paywall.enums.ReferrerChannel
import io.sophi.paywall.enums.ReferrerMedium
import io.sophi.paywall.enums.ReferrerSource
import io.sophi.paywall.enums.VisitorType
import io.sophi.paywall.models.UserDimensions

class Dimensions : TestCase {
    override val name = "Dimensions Tests"
    private val decider = DeciderProvider.decider
    private var allResults = mutableListOf<TestResult>()

    override suspend fun run(): TestResult {
        allResults.clear()
        testUserDimensionsWithAllInputs()
        testDeviceInputsWithAllInputs()

        return TestResult(
            testName = name,
            passed = allResults.all { it.passed },
            executionTimeMs = allResults.sumOf { it.executionTimeMs }
        )
    }

    suspend fun getIndividualResults(): List<TestResult> {
        allResults.clear()
        testUserDimensionsWithAllInputs()
        testDeviceInputsWithAllInputs()
        return allResults.toList()
    }

    private suspend fun testUserDimensionsWithAllInputs() {
        val startTime = System.currentTimeMillis()
        try {
            NativeUserDimensionRepository.update(
                todayPageViews = 3,
                todayPageViewsByArticle = 2,
                todayPageViewsByArticleWithPaywall = 1,
                todayPageViewsByArticleWithRegwall = 0,
                todayTopLevelSections = 2,
                todayTopLevelSectionsByArticle = 1,
                sevenDayPageViews = 7,
                sevenDayPageViewsByArticle = 5,
                sevenDayPageViewsByArticleWithPaywall = 2,
                sevenDayPageViewsByArticleWithRegwall = 1,
                sevenDayTopLevelSections = 4,
                sevenDayTopLevelSectionsByArticle = 3,
                sevenDayVisitCount = 5,
                twentyEightDayPageViews = 15,
                twentyEightDayPageViewsByArticle = 10,
                twentyEightDayPageViewsByArticleWithPaywall = 5,
                twentyEightDayPageViewsByArticleWithRegwall = 2,
                twentyEightDayTopLevelSections = 8,
                twentyEightDayTopLevelSectionsByArticle = 6,
                twentyEightDayVisitCount = 12,
                daysSinceLastVisit = 2,
                visitorType = VisitorType.REGISTERED,
                visitor = UserDimensions.Visitor(
                    session = UserDimensions.Session(
                        campaignName = "SpringSale",
                        referrerDomain = "google.com",
                        referrerMedium = ReferrerMedium.SEARCH,
                        referrerSource = ReferrerSource.GOOGLE,
                        referrerChannel = ReferrerChannel.SEARCH,
                        timestamp = "2024-06-01T12:00:00Z"
                    )
                ),
                timeZone = "America/New_York",
                referrer = ReferrerMedium.SEARCH,
                referrerData = UserDimensions.ReferrerData(
                    medium = ReferrerMedium.SEARCH,
                    source = ReferrerSource.REDDIT,
                    channel = ReferrerChannel.SEARCH
                )
            )
            val decision = decider.decide("test_content_id")
            val encoded = decision.context ?: ""
            val debugInfo = buildDebugInfo("User Dimensions", decision)

            val validationErrors = mutableListOf<String>()

            // Today metrics
            if (!encoded.contains("DA02")) validationErrors.add("daysSinceLastVisit encoding does not match\n  Expected: DA02\n  Actual: ${extractValue(encoded, "DA")}")
            if (!encoded.contains("AA03")) validationErrors.add("todayPageViews encoding does not match\n  Expected: AA03\n  Actual: ${extractValue(encoded, "AA")}")
            if (!encoded.contains("AB02")) validationErrors.add("todayPageViewsByArticle encoding does not match\n  Expected: AB02\n  Actual: ${extractValue(encoded, "AB")}")
            if (!encoded.contains("AC01")) validationErrors.add("todayPageViewsByArticleWithPaywall encoding does not match\n  Expected: AC01\n  Actual: ${extractValue(encoded, "AC")}")
            if (!encoded.contains("AD02")) validationErrors.add("todayTopLevelSections encoding does not match\n  Expected: AD02\n  Actual: ${extractValue(encoded, "AD")}")
            if (!encoded.contains("AE01")) validationErrors.add("todayTopLevelSectionsByArticle encoding does not match\n  Expected: AE01\n  Actual: ${extractValue(encoded, "AE")}")
            if (!encoded.contains("AF00")) validationErrors.add("todayPageViewsByArticleWithRegwall encoding does not match\n  Expected: AF00\n  Actual: ${extractValue(encoded, "AF")}")

            // Seven day metrics
            if (!encoded.contains("BA07")) validationErrors.add("sevenDayPageViews encoding does not match\n  Expected: BA07\n  Actual: ${extractValue(encoded, "BA")}")
            if (!encoded.contains("BB05")) validationErrors.add("sevenDayPageViewsByArticle encoding does not match\n  Expected: BB05\n  Actual: ${extractValue(encoded, "BB")}")
            if (!encoded.contains("BC02")) validationErrors.add("sevenDayPageViewsByArticleWithPaywall encoding does not match\n  Expected: BC02\n  Actual: ${extractValue(encoded, "BC")}")
            if (!encoded.contains("BD04")) validationErrors.add("sevenDayTopLevelSections encoding does not match\n  Expected: BD04\n  Actual: ${extractValue(encoded, "BD")}")
            if (!encoded.contains("BE03")) validationErrors.add("sevenDayTopLevelSectionsByArticle encoding does not match\n  Expected: BE03\n  Actual: ${extractValue(encoded, "BE")}")
            if (!encoded.contains("BF05")) validationErrors.add("sevenDayVisitCount encoding does not match\n  Expected: BF05\n  Actual: ${extractValue(encoded, "BF")}")
            if (!encoded.contains("BG01")) validationErrors.add("sevenDayPageViewsByArticleWithRegwall encoding does not match\n  Expected: BG01\n  Actual: ${extractValue(encoded, "BG")}")

            // Twenty-eight day metrics
            if (!encoded.contains("CA15")) validationErrors.add("twentyEightDayPageViews encoding does not match\n  Expected: CA15\n  Actual: ${extractValue(encoded, "CA")}")
            if (!encoded.contains("CB10")) validationErrors.add("twentyEightDayPageViewsByArticle encoding does not match\n  Expected: CB10\n  Actual: ${extractValue(encoded, "CB")}")
            if (!encoded.contains("CC05")) validationErrors.add("twentyEightDayPageViewsByArticleWithPaywall encoding does not match\n  Expected: CC05\n  Actual: ${extractValue(encoded, "CC")}")
            if (!encoded.contains("CD08")) validationErrors.add("twentyEightDayTopLevelSections encoding does not match\n  Expected: CD08\n  Actual: ${extractValue(encoded, "CD")}")
            if (!encoded.contains("CE06")) validationErrors.add("twentyEightDayTopLevelSectionsByArticle encoding does not match\n  Expected: CE06\n  Actual: ${extractValue(encoded, "CE")}")
            if (!encoded.contains("CF12")) validationErrors.add("twentyEightDayVisitCount encoding does not match\n  Expected: CF12\n  Actual: ${extractValue(encoded, "CF")}")
            if (!encoded.contains("CG02")) validationErrors.add("twentyEightDayPageViewsByArticleWithRegwall encoding does not match\n  Expected: CG02\n  Actual: ${extractValue(encoded, "CG")}")

            allResults.add(
                TestResult(
                    testName = "User Dimensions Test",
                    passed = validationErrors.isEmpty(),
                    executionTimeMs = System.currentTimeMillis() - startTime,
                    debugInfo = debugInfo,
                    error = if (validationErrors.isNotEmpty()) validationErrors.joinToString("\n") else null
                )
            )
        } catch (e: Exception) {
            allResults.add(
                TestResult(
                    testName = "User Dimensions Test",
                    passed = false,
                    error = e.message ?: "Unknown error",
                    executionTimeMs = System.currentTimeMillis() - startTime,
                    debugInfo = "Failed to execute test"
                )
            )
        }
    }

    private suspend fun testDeviceInputsWithAllInputs() {
        val startTime = System.currentTimeMillis()
        try {
            NativeUserDimensionRepository.update(
                visitorType = VisitorType.ANONYMOUS,
                visitor = UserDimensions.Visitor(
                    session = UserDimensions.Session(
                        campaignName = "sale",
                        referrerDomain = "google.com",
                        referrerMedium = ReferrerMedium.SEARCH,
                        referrerSource = ReferrerSource.DUCKDUCKGO,
                        referrerChannel = ReferrerChannel.SEARCH,
                        timestamp = "2024-06-01T14:00:00Z"
                    )
                ),
                timeZone = "America/Toronto",
                referrer = ReferrerMedium.SEARCH,
                referrerData = UserDimensions.ReferrerData(
                    medium = ReferrerMedium.SEARCH,
                    source = ReferrerSource.DUCKDUCKGO,
                    channel = ReferrerChannel.SEARCH
                )
            )
            val decision = decider.decide("test_content_id")
            val inputs = decision.inputs ?: ""
            val debugInfo = buildDebugInfo("Device Inputs", decision)

            val validationErrors = mutableListOf<String>()

            // Visitor
            if (!inputs.contains("HB:02")) validationErrors.add("assignedGroup encoding does not match\n  Expected: HB:02\n  Actual: ${extractValue(inputs, "HB:")}")
            if (!inputs.contains("FA:00")) validationErrors.add("visitorType encoding does not match\n  Expected: FA:00\n  Actual: ${extractValue(inputs, "FA:")}")

            // Referrer Data
            if (!inputs.contains("DE:01")) validationErrors.add("referrer medium encoding does not match\n  Expected: DE:01\n  Actual: ${extractValue(inputs, "DE:")}")
            if (!inputs.contains("DF:08")) validationErrors.add("referrer source encoding does not match\n  Expected: DF:08\n  Actual: ${extractValue(inputs, "DF:")}")
            if (!inputs.contains("DG:01")) validationErrors.add("referrer channel encoding does not match\n  Expected: DG:01\n  Actual: ${extractValue(inputs, "DG:")}")

            // Device
            if (!inputs.contains("EA:ios")) validationErrors.add("os encoding does not match\n  Expected: EA:ios\n  Actual: ${extractValue(inputs, "EA:")}")
            if (!inputs.contains("EB:06")) validationErrors.add("type encoding does not match\n  Expected: EB:06\n  Actual: ${extractValue(inputs, "EB:")}")
            if (!inputs.contains("EC:app")) validationErrors.add("viewer encoding does not match\n  Expected: EC:app\n  Actual: ${extractValue(inputs, "EC:")}")

            // Timezone and Hour
            if (!inputs.contains("DD:14")) validationErrors.add("hourOfDay encoding does not match\n  Expected: DD:14\n  Actual: ${extractValue(inputs, "DD:")}")
            if (!inputs.contains("DB:America/Toronto")) validationErrors.add("timeZone encoding does not match\n  Expected: DB:America/Toronto\n  Actual: ${extractValue(inputs, "DB:")}")

            // Visitor Session
            if (!inputs.contains("GA:sale")) validationErrors.add("session campaignName encoding does not match\n  Expected: GA:sale\n  Actual: ${extractValue(inputs, "GA:")}")
            if (!inputs.contains("GB:google.com")) validationErrors.add("session referrerDomain encoding does not match\n  Expected: GB:google.com\n  Actual: ${extractValue(inputs, "GB:")}")
            if (!inputs.contains("GC:search")) validationErrors.add("session referrerMedium encoding does not match\n  Expected: GC:search\n  Actual: ${extractValue(inputs, "GC:")}")
            if (!inputs.contains("GD:duckduckgo")) validationErrors.add("session referrerSource encoding does not match\n  Expected: GD:duckduckgo\n  Actual: ${extractValue(inputs, "GD:")}")
            if (!inputs.contains("GE:search")) validationErrors.add("session referrerChannel encoding does not match\n  Expected: GE:search\n  Actual: ${extractValue(inputs, "GE:")}")

            allResults.add(
                TestResult(
                    testName = "Device Inputs Test",
                    passed = validationErrors.isEmpty(),
                    executionTimeMs = System.currentTimeMillis() - startTime,
                    debugInfo = debugInfo,
                    error = if (validationErrors.isNotEmpty()) validationErrors.joinToString("\n") else null
                )
            )
        } catch (e: Exception) {
            allResults.add(
                TestResult(
                    testName = "Device Inputs Test",
                    passed = false,
                    error = e.message ?: "Unknown error",
                    executionTimeMs = System.currentTimeMillis() - startTime,
                    debugInfo = "Failed to execute test"
                )
            )
        }
    }

    private fun buildDebugInfo(testName: String, decision: Any?): String {
        return if (decision != null) {
            """
            $testName Decision Response:
            Decision: $decision
            """.trimIndent()
        } else {
            "$testName - No decision response"
        }
    }

    private fun extractValue(text: String, prefix: String): String {
        val regex = Regex("$prefix\\w*")
        val match = regex.find(text)
        return match?.value ?: "NOT FOUND"
    }
}
