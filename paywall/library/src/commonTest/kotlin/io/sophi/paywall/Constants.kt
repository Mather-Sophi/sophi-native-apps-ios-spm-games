package io.sophi.paywall

// Paywall API Responses

val PAYWALL_API_NOWALL_RESPONSE = """
{
    "id": "f1426252-8561-4d5c-a086-9d8985bdf81e",
    "outcome": {
        "wallVisibility": "never",
        "wallVisibilityCode": 0
    },
    "createdAt": "2026-01-16T18:50:29",
    "trigger": {
        "runtimeModelVersion": "explorer:1.0.0",
        "parameters": {
            "score": 0.6526883622123021,
            "threshold": 0.5,
            "isAnonymous": false
        }
    },
    "trace": "z006550e006550v000050",
    "paywallScore": 0,
    "customOutput": {
        "1": 0
    }
}
""".trimIndent()

val PAYWALL_API_PAYWALL_RESPONSE = """
{
    "id": "4df3ad44-77f3-4fce-9da6-80d897fcd091",
    "outcome": {
        "wallVisibility": "always",
        "wallType": "paywall",
        "wallVisibilityCode": 1,
        "wallTypeCode": 1
    },
    "createdAt": "2026-01-16T19:06:48",
    "trigger": {
        "runtimeModelVersion": "explorer:1.0.0",
        "parameters": {
            "score": 0.4414979146604512,
            "threshold": 0.5,
            "isAnonymous": false
        }
    },
    "trace": "z114450e114450v000050",
    "paywallScore": 0,
    "customOutput": {
        "1": 0
    }
}
""".trimIndent()

// Configuration API response

val CONFIGURATION_API_SUCCESS_RESPONSE = """
{
  "definition": "test.sophi.io",
  "policy": {
    "thresholds": {
      "user": {
        "paywall": {
          "anonymous": 0.3,
          "registered": 0.5
        },
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
        "isRandomized": true,
        "visitorBonusParameters": {},
        "visitorRegwallBonusParameters": {
          "hourOfDay": {
            "0": -0.44,
            "2": -0.12,
            "4": -0.35,
            "6": -0.17,
            "8": -0.01,
            "10": 0.25,
            "12": 0.29,
            "14": 0.22,
            "16": -0.07,
            "18": 0.12,
            "20": -0.14,
            "22": -0.26
          },
          "daysSinceLastVisit": {
            "0": 0.05,
            "1": -0.31,
            "2": -0.44,
            "3": -0.21,
            "4": -0.16,
            "5": -0.33
          },
          "referrer": {
            "campaign": 1.07,
            "direct": 0.29,
            "internal": 3.31,
            "other": 1.62,
            "search": -0.7,
            "social": 0.23
          },
          "sevenDayPageViewsByArticle": {
            "0": 0.45,
            "1": -0.41,
            "2": -0.53,
            "3": -0.62,
            "4": -0.68,
            "5": -0.6,
            "6": -0.68,
            "7": -0.68,
            "8": -0.91,
            "9": -0.85,
            "10": -0.85,
            "15": -0.82
          },
          "todayPageViewsByArticle": {
            "0": 1.0,
            "1": -0.57,
            "2": -0.2,
            "3": -0.2,
            "4": -0.09,
            "5": 0.07,
            "6": -0.11,
            "7": -0.42,
            "8": -0.45,
            "10": -0.57
          },
          "todayTopLevelSections": {
            "1": -0.06,
            "2": 0.41,
            "3": -0.1,
            "4": -0.38,
            "5": 0.29
          },
          "todayTopLevelSectionsByArticle": {
            "0": 1.0,
            "1": -0.46,
            "2": -0.48,
            "3": -0.72,
            "4": -0.42,
            "5": -0.37
          },
          "viewer": {
            "android webview": 0.67,
            "chrome": -0.31,
            "edge": 1.93,
            "firefox": 0.88,
            "safari": 0.49,
            "safari (in-app)": 1.18,
            "samsung internet": -0.23
          },
          "sevenDaysStopRate": {
            "0.0": 0.28,
            "0.1": -0.65,
            "0.2": -0.46,
            "0.3": -0.64,
            "0.4": -0.89,
            "0.5": -0.67,
            "0.6": -0.82,
            "0.7": -0.73,
            "0.8": -0.75,
            "0.9": -0.86,
            "1.0": -0.6
          },
          "todayStopRate": {
            "0.0": 0.05,
            "0.2": -0.06,
            "0.3": -0.42,
            "0.4": -0.57,
            "0.5": -0.64,
            "0.6": -0.46,
            "0.7": -0.82,
            "0.8": -0.69,
            "1.0": 3.19
          },
          "deviceOSAndType": {
            "other": {
              "chrome os": 5.6,
              "linux": -0.11,
              "mac os": 1.28,
              "windows": 1.43
            },
            "mobile": {
              "android": -0.54,
              "ios": 0.4
            },
            "tablet": {
              "android": -0.27,
              "ios": 0.13
            }
          },
          "session": {}
        }
      }
    }
  ],
  "userPropertyCodes": [
    {
      "key": "countryCode",
      "name": "countryCode",
      "type": "string"
    },
    {
      "key": "2",
      "name": "dummyField",
      "type": "string"
    }
  ],
  "contentPropertyCodes": [
    {
      "key": "1",
      "name": "section",
      "type": "string"
    }
  ]
}
""".trimIndent()