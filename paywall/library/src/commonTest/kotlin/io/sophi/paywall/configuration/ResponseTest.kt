package io.sophi.paywall.configuration

import kotlinx.serialization.json.Json
import kotlin.test.*

class ResponseTest {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Test
    fun `deserialize and verify ScoreBounds with default and custom values`() {
        // Test default values
        val defaultJson = """{"min": 0.01, "max": 0.99}""".trimIndent()
        val defaultBounds = json.decodeFromString<ScoreBounds>(defaultJson)
        assertEquals(0.01, defaultBounds.min)
        assertEquals(0.99, defaultBounds.max)

        // Test custom values
        val customJson = """{"min": 0.05, "max": 0.95}""".trimIndent()
        val customBounds = json.decodeFromString<ScoreBounds>(customJson)
        assertEquals(0.05, customBounds.min)
        assertEquals(0.95, customBounds.max)

        // Test default constructor
        val constructorBounds = ScoreBounds()
        assertEquals(0.01, constructorBounds.min)
        assertEquals(0.99, constructorBounds.max)
    }

    @Test
    fun `serialize and deserialize round trip for ScoreBounds`() {
        val originalScoreBounds = ScoreBounds(min = 0.05, max = 0.95)
        val serialized = json.encodeToString(ScoreBounds.serializer(), originalScoreBounds)
        val deserialized = json.decodeFromString<ScoreBounds>(serialized)

        assertEquals(originalScoreBounds.min, deserialized.min)
        assertEquals(originalScoreBounds.max, deserialized.max)
    }


    @Test
    fun `deserialize Parameters with various ScoreBounds configurations`() {
        val defaultBoundsJson = """
            {
                "avgScore": 0.5,
                "avgBonus": 0.1,
                "avgRegwallBonus": 0.0,
                "isRandomized": false,
                "scoreRandomizationFactor": 0.03,
                "scoreBounds": {"min": 0.01, "max": 0.99},
                "visitorPaywallBonusParameters": {},
                "regwallBonusParameters": {}
            }
        """.trimIndent()
        val defaultParams = json.decodeFromString<Parameters>(defaultBoundsJson)
        assertEquals(0.5, defaultParams.avgScore)
        assertEquals(0.01, defaultParams.scoreBounds.min)
        assertEquals(0.99, defaultParams.scoreBounds.max)

        // Test with custom ScoreBounds
        val customBoundsJson = """
            {
                "avgScore": 0.6,
                "avgBonus": 0.2,
                "avgRegwallBonus": 0.15,
                "isRandomized": true,
                "scoreRandomizationFactor": 0.05,
                "scoreBounds": {"min": 0.02, "max": 0.98},
                "visitorPaywallBonusParameters": {},
                "regwallBonusParameters": {}
            }
        """.trimIndent()
        val customParams = json.decodeFromString<Parameters>(customBoundsJson)
        assertEquals(0.6, customParams.avgScore)
        assertEquals(0.02, customParams.scoreBounds.min)
        assertEquals(0.98, customParams.scoreBounds.max)
        assertTrue(customParams.isRandomized)
    }

    @Test
    fun `deserialize Parameters with bonus parameters and null values`() {
        val parametersJson = """
            {
                "avgScore": 0.5,
                "avgBonus": 0.1,
                "avgRegwallBonus": 0.0,
                "isRandomized": false,
                "scoreRandomizationFactor": 0.03,
                "scoreBounds": {"min": 0.01, "max": 0.99},
                "visitorPaywallBonusParameters": {
                    "anonymous": {
                        "daysSinceLastVisit": {"0": 0.32},
                        "hourOfDay": {"8": -0.04}
                    },
                    "registered": {
                        "referrer": {"search": 1.1}
                    }
                },
                "regwallBonusParameters": {
                    "daysSinceLastVisit": {"0": 0.5},
                    "hourOfDay": {"12": 0.1},
                    "referrer": null
                }
            }
        """.trimIndent()

        val parameters = json.decodeFromString<Parameters>(parametersJson)

        // Verify basic properties
        assertEquals(0.5, parameters.avgScore)

        // Verify visitor paywall bonus parameters
        assertEquals(2, parameters.visitorPaywallBonusParameters.size)
        assertTrue(parameters.visitorPaywallBonusParameters.containsKey("anonymous"))
        assertTrue(parameters.visitorPaywallBonusParameters.containsKey("registered"))
        assertEquals(0.32, parameters.visitorPaywallBonusParameters["anonymous"]?.daysSinceLastVisit?.get(0))

        // Verify regwall bonus parameters
        assertNotNull(parameters.regwallBonusParameters.daysSinceLastVisit)
        assertEquals(0.5, parameters.regwallBonusParameters.daysSinceLastVisit?.get(0))
        assertNull(parameters.regwallBonusParameters.referrer)
    }

    @Test
    fun `serialize and deserialize round trip for Parameters`() {
        val originalParameters = Parameters(
            avgScore = 0.6,
            avgBonus = 0.2,
            avgRegwallBonus = 0.15,
            isRandomized = true,
            scoreRandomizationFactor = 0.05,
            scoreBounds = ScoreBounds(min = 0.02, max = 0.98),
            visitorPaywallBonusParameters = emptyMap(),
            regwallBonusParameters = BonusParameters()
        )

        val serialized = json.encodeToString(Parameters.serializer(), originalParameters)
        val deserialized = json.decodeFromString<Parameters>(serialized)

        assertEquals(originalParameters.avgScore, deserialized.avgScore)
        assertEquals(originalParameters.avgBonus, deserialized.avgBonus)
        assertEquals(originalParameters.avgRegwallBonus, deserialized.avgRegwallBonus)
        assertEquals(originalParameters.isRandomized, deserialized.isRandomized)
        assertEquals(originalParameters.scoreRandomizationFactor, deserialized.scoreRandomizationFactor)
        assertEquals(originalParameters.scoreBounds.min, deserialized.scoreBounds.min)
        assertEquals(originalParameters.scoreBounds.max, deserialized.scoreBounds.max)
    }

    @Test
    fun `deserialize BonusParameters with comprehensive parameter coverage`() {
        val bonusParametersJson = """
            {
                "hourOfDay": {
                    "0": -0.44,
                    "8": -0.01,
                    "12": 0.29,
                    "22": -0.26
                },
                "daysSinceLastVisit": {
                    "0": 0.05,
                    "1": -0.31,
                    "2": -0.44
                },
                "referrer": {
                    "campaign": 0.57,
                    "direct": -0.02,
                    "search": -3.0
                },
                "visitorType": {
                    "anonymous": 0.1,
                    "registered": -0.1
                },
                "os": {
                    "ios": 0.15,
                    "android": -0.05
                }
            }
        """.trimIndent()

        val bonusParameters = json.decodeFromString<BonusParameters>(bonusParametersJson)

        // Verify hourOfDay
        assertNotNull(bonusParameters.hourOfDay)
        assertEquals(4, bonusParameters.hourOfDay?.size)
        assertEquals(-0.44, bonusParameters.hourOfDay?.get(0))
        assertEquals(0.29, bonusParameters.hourOfDay?.get(12))

        // Verify daysSinceLastVisit
        assertNotNull(bonusParameters.daysSinceLastVisit)
        assertEquals(3, bonusParameters.daysSinceLastVisit?.size)
        assertEquals(0.05, bonusParameters.daysSinceLastVisit?.get(0))
        assertEquals(-0.44, bonusParameters.daysSinceLastVisit?.get(2))

        // Verify referrer
        assertNotNull(bonusParameters.referrer)
        assertEquals(0.57, bonusParameters.referrer?.get("campaign"))
        assertEquals(-3.0, bonusParameters.referrer?.get("search"))

        // Verify visitorType and os
        assertNotNull(bonusParameters.visitorType)
        assertEquals(0.1, bonusParameters.visitorType?.get("anonymous"))
        assertNotNull(bonusParameters.os)
        assertEquals(0.15, bonusParameters.os?.get("ios"))
    }

    // ==================== Thresholds Tests ====================

    @Test
    fun `deserialize Thresholds and ControlPolicy with various configurations`() {
        val thresholdsJson = """
            {
                "user": {
                    "paywall": {
                        "anonymous": 0.3,
                        "registered": 0.5
                    },
                    "regwall": 0.8
                }
            }
        """.trimIndent()

        val thresholds = json.decodeFromString<Thresholds>(thresholdsJson)

        assertNotNull(thresholds.user)
        assertNotNull(thresholds.user.paywall)
        assertEquals(0.3, thresholds.user.paywall.anonymous)
        assertEquals(0.5, thresholds.user.paywall.registered)
        assertEquals(0.8, thresholds.user.regwall)
    }

    @Test
    fun `deserialize Experience with single and multiple models`() {
        val singleModelJson = """
            {
                "definition": "test.sophi.io",
                "policy": {
                    "thresholds": {
                        "user": {
                            "paywall": {"anonymous": 0.3, "registered": 0.5},
                            "regwall": 0.8
                        }
                    }
                },
                "activeModels": [
                    {
                        "id": "on-device",
                        "parameters": {
                            "avgScore": 0.5,
                            "avgBonus": 0.1,
                            "avgRegwallBonus": 0.0,
                            "isRandomized": false,
                            "scoreRandomizationFactor": 0.03,
                            "scoreBounds": {"min": 0.01, "max": 0.99},
                            "visitorPaywallBonusParameters": {},
                            "regwallBonusParameters": {}
                        }
                    }
                ]
            }
        """.trimIndent()

        val singleExperience = json.decodeFromString<Experience>(singleModelJson)
        assertEquals("test.sophi.io", singleExperience.definition)
        assertEquals(1, singleExperience.activeModels.size)
        assertEquals("on-device", singleExperience.activeModels[0].id)
        assertEquals(0.01, singleExperience.activeModels[0].parameters.scoreBounds.min)

        // Multiple models with model lookup
        val multiModelJson = """
            {
                "definition": "test.sophi.io",
                "policy": {
                    "thresholds": {
                        "user": {
                            "paywall": {"anonymous": 0.3, "registered": 0.5},
                            "regwall": 0.8
                        }
                    }
                },
                "activeModels": [
                    {
                        "id": "on-device",
                        "parameters": {
                            "avgScore": 0.5,
                            "avgBonus": 0.1,
                            "avgRegwallBonus": 0.0,
                            "isRandomized": false,
                            "scoreRandomizationFactor": 0.03,
                            "scoreBounds": {"min": 0.01, "max": 0.99},
                            "visitorPaywallBonusParameters": {},
                            "regwallBonusParameters": {}
                        }
                    },
                    {
                        "id": "remote-model",
                        "parameters": {
                            "avgScore": 0.6,
                            "avgBonus": 0.2,
                            "avgRegwallBonus": 0.15,
                            "isRandomized": true,
                            "scoreRandomizationFactor": 0.05,
                            "scoreBounds": {"min": 0.02, "max": 0.98},
                            "visitorPaywallBonusParameters": {},
                            "regwallBonusParameters": {}
                        }
                    }
                ]
            }
        """.trimIndent()

        val multiExperience = json.decodeFromString<Experience>(multiModelJson)
        assertEquals(2, multiExperience.activeModels.size)

        // Test getModelById
        val onDeviceModel = multiExperience.getModelById("on-device")
        assertNotNull(onDeviceModel)
        assertEquals(0.5, onDeviceModel.parameters.avgScore)

        val remoteModel = multiExperience.getModelById("remote-model")
        assertNotNull(remoteModel)
        assertEquals(0.6, remoteModel.parameters.avgScore)

        val nonExistentModel = multiExperience.getModelById("non-existent")
        assertNull(nonExistentModel)
    }

    @Test
    fun `deserialize Experience with property codes`() {
        val experienceJson = """
            {
                "definition": "test.sophi.io",
                "policy": {
                    "thresholds": {
                        "user": {
                            "paywall": {"anonymous": 0.3, "registered": 0.5},
                            "regwall": 0.8
                        }
                    }
                },
                "activeModels": [],
                "propertyCodes": {
                    "user": [
                        {
                            "key": "premium_subscriber",
                            "name": "Premium Subscriber",
                            "type": "boolean"
                        },
                        {
                            "key": "user_age",
                            "name": "User Age",
                            "type": "integer"
                        }
                    ],
                    "content": [
                        {
                            "key": "article_category",
                            "name": "Article Category",
                            "type": "string"
                        }
                    ]
                }
            }
        """.trimIndent()

        val experience = json.decodeFromString<Experience>(experienceJson)

        assertEquals("test.sophi.io", experience.definition)
        assertNotNull(experience.propertyCodes)
        assertEquals(2, experience.propertyCodes.user?.size)
        assertEquals(1, experience.propertyCodes.content?.size)
        assertEquals("premium_subscriber", experience.propertyCodes.user?.get(0)?.key)
        assertEquals("article_category", experience.propertyCodes.content?.get(0)?.key)
    }
}

