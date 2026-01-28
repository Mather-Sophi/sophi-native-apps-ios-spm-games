package io.sophi.paywall.configuration

val FALLBACK_PARAMETERS: Lazy<Parameters> = lazy {
    Parameters(
        avgScore = 0.5,
        avgBonus = 0.1,
        avgRegwallBonus = 0.0,
        isRandomized = false,
        visitorBonusParameters = mapOf(

        ),
        visitorRegwallBonusParameters = BonusParameters(
            hourOfDay = mapOf(
                0 to -0.44, 2 to -0.43, 4 to -0.24, 6 to 0.11, 8 to 0.16,
                10 to 0.14, 12 to 0.1, 14 to 0.13, 16 to -0.18, 18 to -0.03,
                20 to 0.0, 22 to -0.22
            ),
            daysSinceLastVisit = mapOf(),
            referrer = mapOf(
                "campaign" to 0.57, "direct" to -0.02, "internal" to 4.72,
                "other" to 2.74, "search" to -3.0, "social" to 0.56
            ),
            sevenDayPageViewsByArticle = mapOf(
                0 to 0.41,
                1 to -0.07,
                2 to -0.41,
                3 to -0.37,
                4 to -0.41,
                5 to -0.56,
                6 to -0.28,
                7 to -0.56,
                8 to -0.47,
                9 to -0.62,
                10 to -0.59,
                11 to -0.59,
                12 to -0.59,
                13 to -0.59,
                14 to -0.59,
                15 to -0.78,
                16 to -0.78,
                17 to -0.78,
                18 to -0.78,
                19 to -0.78
            ),
            todayPageViewsByArticle = mapOf(
                0 to 0.65, 1 to -0.26, 2 to 0.34, 3 to 0.71, 4 to 0.73,
                5 to 0.83, 6 to 0.58, 7 to 2.18, 8 to 0.23, 9 to 0.75,
                10 to -0.55, 11 to -0.55, 12 to -0.55, 13 to -0.55, 14 to -0.55
            ),
            todayTopLevelSections = mapOf(
                1 to -0.18, 2 to 0.61, 3 to 0.73, 4 to 1.04, 5 to 0.79,
                6 to -0.54, 7 to -0.02
            ),
            todayTopLevelSectionsByArticle = mapOf(
                0 to 0.65, 1 to -0.04, 2 to -0.02, 3 to 0.02, 4 to -0.19,
                5 to -0.2, 6 to -0.07
            ),
            viewer = mapOf(
                "android" to -0.4, "ios" to -0.36
            ),
            sevenDaysStopRate = mapOf(
                0.0 to 0.33, 0.1 to 0.69, 0.2 to -0.66, 0.3 to -0.42,
                0.4 to -0.69, 0.5 to -0.48, 0.6 to -0.61, 0.7 to -0.52,
                0.8 to -0.56, 0.9 to -0.56, 1.0 to -0.33
            ),
            todayStopRate = mapOf(
                0.0 to 0.68, 0.1 to 4.43, 0.2 to 0.66, 0.3 to 1.55,
                0.4 to 3.05, 0.5 to 0.65, 0.6 to 1.33, 0.7 to 0.59,
                0.8 to 0.34, 0.9 to 0.66, 1.0 to -0.14
            ),
            deviceOSAndType = mapOf(
                "android" to mapOf(
                    "mobile" to -0.59,
                    "tablet" to -0.51
                ),
                "ios" to mapOf(
                    "mobile" to 0.82,
                    "tablet" to 0.05
                )
            ),
            session = mapOf()
        )
    )
}

val FALLBACK_EXPERIENCE: Lazy<Experience> = lazy {
    Experience(
        definition = "fallback-experience",
        activeModels = listOf(
            ActiveModel(
                id = "on-device",
                parameters = FALLBACK_PARAMETERS.value
            )
        ),
        policy = ControlPolicy(
            thresholds = Thresholds()
        )
    )
}